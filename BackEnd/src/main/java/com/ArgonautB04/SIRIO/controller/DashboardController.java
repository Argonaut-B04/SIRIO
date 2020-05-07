package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.DashboardDTO;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Dashboard")
public class DashboardController {

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    @Autowired
    private TemuanRisikoRestService temuanRisikoRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    /**
     * Mengambil seluruh komponen dashboard
     *
     * @return komponen dashboard (rekomendasi, temuan risiko, pemeriksaan)
     */
    @GetMapping("/getAll")
    private BaseResponse<DashboardDTO> getAllDashboardComponent(
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
//        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");
        Role role = employee.getRole();

        List<Rekomendasi> daftarRekomendasi;
        List<TemuanRisiko> daftarTemuanRisiko;
        List<TugasPemeriksaan> daftarTugasPemeriksaan;
        if (role.getNamaRole().equals("Branch Manager")) {
            KantorCabang kantorCabang =
                    kantorCabangRestService.getByPemilik(employee);
            daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
            List<HasilPemeriksaan> daftarHasilPemeriksaan =
                    hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            List<KomponenPemeriksaan> daftarKomponenPemeriksaan =
                    komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        } else {
            daftarRekomendasi = rekomendasiRestService.getAll();
            daftarTemuanRisiko = temuanRisikoRestService.getAll();
            daftarTugasPemeriksaan = tugasPemeriksaanRestService.getAll();
        }

        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setJumlahRekomendasi(daftarRekomendasi.size());
        dashboardDTO.setJumlahTemuan(daftarTemuanRisiko.size());
        dashboardDTO.setJumlahPemeriksaan(daftarTugasPemeriksaan.size());

        List<BuktiPelaksanaan> buktiList = buktiPelaksanaanRestService.getByDaftarRekomendasi(daftarRekomendasi);
        List<BuktiPelaksanaan> listRekomendasiImplemented = new ArrayList<BuktiPelaksanaan>();
        List<BuktiPelaksanaan> listRekomendasiNotImplemented = new ArrayList<BuktiPelaksanaan>();
        List<Rekomendasi> listRekomendasiOverdue = new ArrayList<Rekomendasi>();

        LocalDate waktuSaatIni = LocalDate.now();
        for (Rekomendasi rekomendasi : daftarRekomendasi) {
            LocalDate tenggatWaktu = rekomendasi.getTenggatWaktu();
            if (tenggatWaktu != null) {
                int durasi = (int) ChronoUnit.DAYS.between(waktuSaatIni, tenggatWaktu);
                if (durasi == 0 | durasi < 0) {
                    listRekomendasiOverdue.add(rekomendasi);
                }
            }
            for (BuktiPelaksanaan buktiPelaksanaan : buktiList) {
                if (buktiPelaksanaan.getRekomendasi().equals(rekomendasi)) {
                    if (buktiPelaksanaan.getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
                        listRekomendasiImplemented.add(buktiPelaksanaan);
                    } else {
                        listRekomendasiNotImplemented.add(buktiPelaksanaan);
                    }
                }
            }
        }

        int rekomendasiOverdue = listRekomendasiOverdue.size()
                - (listRekomendasiNotImplemented.size() + listRekomendasiImplemented.size());
        int rekomendasiImplemented = listRekomendasiImplemented.size();
        int rekomendasiNotImplemented = listRekomendasiNotImplemented.size()
                + (daftarRekomendasi.size() - buktiList.size());

        dashboardDTO.setJumlahRekomendasiOverdue(rekomendasiOverdue);
        dashboardDTO.setJumlahRekomendasiImplemented(rekomendasiImplemented);
        dashboardDTO.setJumlahRekomendasiNotImplemented(rekomendasiNotImplemented);

        dashboardDTO.setPersenRekomendasiOverdue((float) rekomendasiOverdue*100/daftarRekomendasi.size());
        dashboardDTO.setPersenRekomendasiImplemented((float) rekomendasiImplemented*100/daftarRekomendasi.size());
        dashboardDTO.setPersenRekomendasiNotImplemented((float) rekomendasiNotImplemented*100/daftarRekomendasi.size());

        return new BaseResponse<>(200, "success", dashboardDTO);
    }

}
