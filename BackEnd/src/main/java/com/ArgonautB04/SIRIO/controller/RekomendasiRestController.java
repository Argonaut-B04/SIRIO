package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Rekomendasi")
public class RekomendasiRestController {

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    @Autowired
    private ReminderRestService reminderRestService;

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

    /**
     * Mengambil suatu rekomendasi
     *
     * @param idRekomendasi identifier rekomendasi
     * @return detail rekomendasi
     */
    @GetMapping("/{idRekomendasi}")
    private BaseResponse<RekomendasiDTO> getDetailRekomendasi(
            @PathVariable("idRekomendasi") int idRekomendasi, Principal principal
    ) {
        BaseResponse<RekomendasiDTO> response = new BaseResponse<>();
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "tabel rekomendasi");
        Rekomendasi rekomendasi = rekomendasiRestService.validateExistInById(idRekomendasi);
        RekomendasiDTO result = new RekomendasiDTO();
        result.setId(rekomendasi.getIdRekomendasi());
        result.setKeterangan(rekomendasi.getKeterangan());

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh rekomendasi yang terhubung dengan suatu kantor cabang
     *
     * @param idKantor identifier kantor cabang
     * @return daftar rekomendasi yang terhubung dengan kantor cabang
     */
    @GetMapping("/getAllByKantorCabang/{idKantor}")
    private BaseResponse<List<Rekomendasi>> getAllRekomendasiUntukKantorCabang(
            @PathVariable("idKantor") int idKantor,
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");

        KantorCabang kantorCabang = kantorCabangRestService.validateExistById(idKantor);

        List<TugasPemeriksaan> daftarTugasPemeriksaan =
                tugasPemeriksaanRestService.
                        getByKantorCabang(kantorCabang);

        List<HasilPemeriksaan> daftarHasilPemeriksaan =
                hasilPemeriksaanRestService
                        .getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);

        List<KomponenPemeriksaan> daftarKomponenPemeriksaan =
                komponenPemeriksaanRestService
                        .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);

        List<Rekomendasi> result =
                rekomendasiRestService
                        .getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);

        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengubah tenggat waktu untuk rekomendasi spesifik.
     *
     * @return objek rekomendasi yang telah diubah
     */
    @PostMapping("/tenggatWaktu")
    private BaseResponse<Rekomendasi> ubahTenggatWaktu(
            @RequestBody RekomendasiDTO rekomendasiDTO,
            Principal principal
    ) {
        // validasi employee ada dan punya akses tenggat waktu
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "tenggat waktu");

        // validasi rekomendasi yang mau diset tenggat waktu ada di database
        Integer idRekomendasi = rekomendasiDTO.getId();
        Rekomendasi rekomendasi = rekomendasiRestService.validateExistInById(idRekomendasi);

        // validasi tenggat waktu yang dimasukan
        LocalDate tenggatWaktuBaru = rekomendasiDTO.getTenggatWaktuDate();
        rekomendasiRestService.validateDateInputMoreThanToday(tenggatWaktuBaru);

        // validasi status rekomendasi memungkinkan pengaturan tenggat waktu
        rekomendasiRestService.validateDeadlineCanBeSet(rekomendasi);

        // mengatur tenggat waktu dan simpan perubahan
        rekomendasi.setTenggatWaktu(tenggatWaktuBaru);
        Rekomendasi result = rekomendasiRestService.buatAtauSimpanPerubahanRekomendasi(rekomendasi, true);

        // membuat 3 reminder: 1 hari sebelum, 3 hari sebelum, dan 1 minggu sebelum
        reminderRestService.generateDefaultReminder(employee, rekomendasi);

        // kirim response
        return new BaseResponse<>(200, "success", result);
    }
}