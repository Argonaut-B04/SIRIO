package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
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
     * Mengambil seluruh rekomendasi yang terhubung dengan user yang sedang login
     *
     * @return daftar rekomendasi yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RekomendasiDTO>> getAllRekomendasiUntukLoggedInUser(
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");

        Role role = employee.getRole();

        List<Rekomendasi> daftarRekomendasi;
        if (role.getNamaRole().equals("Branch Manager")) {
            KantorCabang kantorCabang =
                    kantorCabangRestService
                            .getByPemilik(employee);

            List<TugasPemeriksaan> daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService
                            .getByKantorCabang(kantorCabang);

            List<HasilPemeriksaan> daftarHasilPemeriksaan =
                    hasilPemeriksaanRestService
                            .getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);

            List<KomponenPemeriksaan> daftarKomponenPemeriksaan =
                    komponenPemeriksaanRestService
                            .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);

            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);

        } else if (role.getNamaRole().equals("Super QA Officer Operational Risk")
                | role.getNamaRole().equals("Manajer Operational Risk")) {
            daftarRekomendasi = rekomendasiRestService.getAll();

        } else {
            daftarRekomendasi = rekomendasiRestService.getByPembuat(employee);
        }


        List<RekomendasiDTO> resultDTO = new ArrayList<>();
        LocalDate waktuSaatIni = LocalDate.now();
        for (Rekomendasi rekomendasi : daftarRekomendasi) {
            RekomendasiDTO rekomendasiDTO = new RekomendasiDTO();

            // Memasukan id dan keterangan
            rekomendasiDTO.setId(rekomendasi.getIdRekomendasi());
            rekomendasiDTO.setKeterangan(rekomendasi.getKeterangan());

            // Memasukan tenggat waktu
            LocalDate tenggatWaktu = rekomendasi.getTenggatWaktu();
            rekomendasiDTO.setTenggatWaktuDate(tenggatWaktu);

            // Memasukan durasi hingga tenggat waktu
            if (tenggatWaktu != null) {
                int durasi = (int) ChronoUnit.DAYS.between(waktuSaatIni, tenggatWaktu);
                if (durasi < 0) {
                    durasi = 0;
                }
                String durasiFinal = durasi + " Hari";
                rekomendasiDTO.setDurasi(durasiFinal);
            }

            // Memasukan status rekomendasi
            rekomendasiDTO.setStatus(rekomendasi.getStatusRekomendasi().getNamaStatus());

            // Memasukan status bukti untuk rekomendasi
            List<BuktiPelaksanaan> buktiList = buktiPelaksanaanRestService.getByDaftarRekomendasi(daftarRekomendasi);
            for (BuktiPelaksanaan buktiPelaksanaan : buktiList) {
                if (buktiPelaksanaan.getRekomendasi().equals(rekomendasi)) {
                    rekomendasiDTO.setStatusBukti(
                            buktiPelaksanaan
                                    .getStatusBuktiPelaksanaan()
                                    .getNamaStatus()
                    );
                }
            }

            // Memasukan daftar kantor cabang setiap rekomendasi
            rekomendasiDTO
                    .setNamaKantorCabang(
                            rekomendasi
                                    .getKomponenPemeriksaan()
                                    .getHasilPemeriksaan()
                                    .getTugasPemeriksaan()
                                    .getKantorCabang()
                                    .getNamaKantor());

            // Memasukan id hasil pemeriksaan setiap rekomendasi
            rekomendasiDTO.setIdHasilPemeriksaan(
                    rekomendasi
                            .getKomponenPemeriksaan()
                            .getHasilPemeriksaan()
                            .getIdHasilPemeriksaan()
            );

            resultDTO.add(rekomendasiDTO);
        }

        return new BaseResponse<>(200, "success", resultDTO);
    }

}
