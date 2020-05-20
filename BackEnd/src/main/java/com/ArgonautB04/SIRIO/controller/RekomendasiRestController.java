package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.services.BuktiPelaksanaanRestService;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.HasilPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.KantorCabangRestService;
import com.ArgonautB04.SIRIO.services.KomponenPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.ReminderRestService;
import com.ArgonautB04.SIRIO.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Rekomendasi")
public class RekomendasiRestController {

    /**
     * Complete and success status code.
     */
    private final int complete = 200;

    /**
     * Bind Recommendation Rest Service.
     */
    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    /**
     * Bind Employee Rest Service.
     */
    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Bind to Tugas Pemeriksaan Rest Service.
     */
    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    /**
     * Bind to Hasil Pemeriksaan Rest Service.
     */
    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    /**
     * Bind to Komponen Pemeriksaan Rest Service.
     */
    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    /**
     * Bind to Branch Manager Rest Service.
     */
    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    /**
     * Bind to Bukti Pelaksanaan Rest Service.
     */
    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    /**
     * Bind to Reminder Rest Service.
     */
    @Autowired
    private ReminderRestService reminderRestService;

    /**
     * Mengambil seluruh rekomendasi yang terhubung
     * dengan user yang sedang login.
     *
     * @param principal object from Spring Security
     * @return daftar rekomendasi yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RekomendasiDTO>>
    getAllRekomendasiUntukLoggedInUser(
            final Principal principal
    ) {
        Employee employee = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(employee, "tabel rekomendasi");

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
                            .getByDaftarTugasPemeriksaan(
                                    daftarTugasPemeriksaan
                            );

            List<KomponenPemeriksaan> daftarKomponenPemeriksaan =
                    komponenPemeriksaanRestService
                            .getByDaftarHasilPemeriksaan(
                                    daftarHasilPemeriksaan
                            );

            daftarRekomendasi = rekomendasiRestService
                    .getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);

        } else if (
                role.getNamaRole().equals("Super QA Officer Operational Risk")
                        || role.getNamaRole().equals("Manajer Operational Risk")
        ) {
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
                int durasi = (int) ChronoUnit.DAYS.between(
                        waktuSaatIni, tenggatWaktu
                );
                if (durasi < 0) {
                    durasi = 0;
                }
                String durasiFinal = durasi + " Hari";
                rekomendasiDTO.setDurasi(durasiFinal);
            }

            // Memasukan status rekomendasi
            rekomendasiDTO.setStatus(
                    rekomendasi.getStatusRekomendasi().getNamaStatus()
            );

            // Memasukan status bukti untuk rekomendasi
            List<BuktiPelaksanaan> buktiList =
                    buktiPelaksanaanRestService
                            .getByDaftarRekomendasi(daftarRekomendasi);
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

        return new BaseResponse<>(complete, "success", resultDTO);
    }

    /**
     * Mengambil suatu rekomendasi.
     *
     * @param principal object from Spring Security
     * @param idRekomendasi identifier rekomendasi
     * @return detail rekomendasi
     */
    @GetMapping("/{idRekomendasi}")
    private BaseResponse<RekomendasiDTO> getDetailRekomendasi(
            final @PathVariable("idRekomendasi") int idRekomendasi,
            final Principal principal
    ) {
        BaseResponse<RekomendasiDTO> response = new BaseResponse<>();
        Employee pengelola = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(pengelola, "tabel rekomendasi");
        Rekomendasi rekomendasi = rekomendasiRestService
                .validateExistInById(idRekomendasi);
        RekomendasiDTO result = new RekomendasiDTO();
        result.setId(rekomendasi.getIdRekomendasi());
        result.setKeterangan(rekomendasi.getKeterangan());
        result.setStatus(rekomendasi.getStatusRekomendasi().getNamaStatus());

        response.setStatus(complete);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh rekomendasi yang terhubung dengan suatu kantor cabang.
     *
     * @param principal object from Spring Security
     * @param idKantor identifier kantor cabang
     * @return daftar rekomendasi yang terhubung dengan kantor cabang
     */
    @GetMapping("/getAllByKantorCabang/{idKantor}")
    private BaseResponse<List<Rekomendasi>> getAllRekomendasiUntukKantorCabang(
            final @PathVariable("idKantor") int idKantor,
            final Principal principal
    ) {
        Employee employee = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(employee, "tabel rekomendasi");

        KantorCabang kantorCabang = kantorCabangRestService
                .validateExistById(idKantor);

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
                        .getByDaftarKomponenPemeriksaan(
                                daftarKomponenPemeriksaan
                        );

        return new BaseResponse<>(complete, "success", result);
    }

    /**
     * Mengubah tenggat waktu untuk rekomendasi spesifik.
     *
     * @param rekomendasiDTO JSON object from frontend
     * @param principal      object from Spring Security
     * @return objek rekomendasi yang telah diubah
     */
    @PostMapping("/tenggatWaktu")
    private BaseResponse<Rekomendasi> ubahTenggatWaktu(
            final @RequestBody RekomendasiDTO rekomendasiDTO,
            final Principal principal
    ) {
        // validasi employee ada dan punya akses tenggat waktu
        Employee employee = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(employee, "tenggat waktu");

        // validasi rekomendasi yang mau diset tenggat waktu ada di database
        Integer idRekomendasi = rekomendasiDTO.getId();
        Rekomendasi rekomendasi = rekomendasiRestService
                .validateExistInById(idRekomendasi);

        // validasi tenggat waktu yang dimasukan
        LocalDate tenggatWaktuBaru = rekomendasiDTO.getTenggatWaktuDate();
        rekomendasiRestService.validateDateInputMoreThanToday(tenggatWaktuBaru);

        // validasi status rekomendasi memungkinkan pengaturan tenggat waktu
        rekomendasiRestService.validateDeadlineCanBeSet(rekomendasi);

        // mengatur tenggat waktu dan simpan perubahan
        rekomendasi.setTenggatWaktu(tenggatWaktuBaru);
        Rekomendasi result = rekomendasiRestService
                .buatAtauSimpanPerubahanRekomendasi(
                        rekomendasi, true
                );

        // membuat 3 reminder:
        // 1 hari sebelum,
        // 3 hari sebelum,
        // dan 1 minggu sebelum
        reminderRestService.generateDefaultReminder(employee, rekomendasi);

        // kirim response
        return new BaseResponse<>(complete, "success", result);
    }
}
