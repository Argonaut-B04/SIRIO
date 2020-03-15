package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/HasilPemeriksaan")
public class HasilPemeriksaanRestController {

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private StatusHasilPemeriksaanRestService statusHasilPemeriksaanRestService;

    @Autowired
    private RiskLevelRestService riskLevelRestService;

    @Autowired
    private RisikoRestService risikoRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private TemuanRisikoRestService temuanRisikoRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private StatusRekomendasiRestService statusRekomendasiRestService;

    /**
     * Mengambil seluruh hasil pemeriksaan
     *
     * @return daftar hasil pemeriksaan
     */
    @GetMapping("/getAll")
    private BaseResponse<List<HasilPemeriksaan>> getAllHasilPemeriksaan() {
        BaseResponse<List<HasilPemeriksaan>> response = new BaseResponse<>();
        List<HasilPemeriksaan> result = hasilPemeriksaanRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh hasil pemeriksaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar hasil pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<HasilPemeriksaan>> getAllHasilPemeriksaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        BaseResponse<List<HasilPemeriksaan>> response = new BaseResponse<>();
        try {
            Employee pembuat = employeeRestService.getById(idPembuat);
            List<HasilPemeriksaan> result = hasilPemeriksaanRestService.getByPembuat(pembuat);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + idPembuat + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Mengambil suatu hasil pemeriksaan
     *
     * @param idHasilPemeriksaan identifier hasil pemeriksaan
     * @return detail hasil pemeriksaan
     */
    @GetMapping("/{idHasilPemeriksaan}")
    private BaseResponse<HasilPemeriksaan> getHasilPemeriksaan(
            @PathVariable("idHasilPemeriksaan") int idHasilPemeriksaan
    ) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();
        try {
            HasilPemeriksaan result = hasilPemeriksaanRestService.getById(idHasilPemeriksaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Hasil Pemeriksaan dengan ID " + idHasilPemeriksaan + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menambah hasil pemeriksaan baru untuk tugas pemeriksaan spesifik
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang akan ditambah
     * @return hasil pemeriksaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<HasilPemeriksaan> tambahHasilPemeriksaan(
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO
    ) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();
        HasilPemeriksaan hasilPemeriksaanTemp = new HasilPemeriksaan();
        hasilPemeriksaanTemp.setStatusHasilPemeriksaan(
                statusHasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getStatus()));

        try {
            Employee pembuat = employeeRestService.getById(hasilPemeriksaanDTO.getIdPembuat());
            hasilPemeriksaanTemp.setPembuat(pembuat);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + hasilPemeriksaanDTO.getIdPembuat() + " tidak ditemukan!"
            );
        }

        try {
            TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.getById(hasilPemeriksaanDTO.getIdTugasPemeriksaan());
            hasilPemeriksaanTemp.setTugasPemeriksaan(tugasPemeriksaan);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tugas Pemeriksaan dengan ID " + hasilPemeriksaanDTO.getIdTugasPemeriksaan() + " tidak ditemukan!"
            );
        }
        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.buatHasilPemeriksaan(hasilPemeriksaanTemp);

        for (KomponenPemeriksaanDTO komponenPemeriksaanData: hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
            KomponenPemeriksaan komponenPemeriksaanTemp = new KomponenPemeriksaan();
            komponenPemeriksaanTemp.setHasilPemeriksaan(hasilPemeriksaan);
            komponenPemeriksaanTemp.setRisiko(risikoRestService.getById(komponenPemeriksaanData.getIdRisiko()));
            if (komponenPemeriksaanData.getIdRiskLevel() != null) {
                komponenPemeriksaanTemp.setRiskLevel(riskLevelRestService.getById(komponenPemeriksaanData.getIdRiskLevel()));
            }
            if (komponenPemeriksaanData.getJumlahSampel() != null) {
                komponenPemeriksaanTemp.setJumlahSampel(komponenPemeriksaanData.getJumlahSampel());
            }
            if (komponenPemeriksaanData.getKeteranganSampel() != null) {
                komponenPemeriksaanTemp.setKeteranganSampel(komponenPemeriksaanData.getKeteranganSampel());
            }
            KomponenPemeriksaan komponenPemeriksaan =
                    komponenPemeriksaanRestService.buatKomponenPemeriksaan(komponenPemeriksaanTemp);

            if(komponenPemeriksaanData.getDaftarTemuanRisiko() != null)
                for (TemuanRisikoDTO temuanRisikoData: komponenPemeriksaanData.getDaftarTemuanRisiko()) {
                    TemuanRisiko temuanRisiko = new TemuanRisiko();
                    temuanRisiko.setKomponenPemeriksaan(komponenPemeriksaan);
                    temuanRisiko.setKeterangan(temuanRisikoData.getKeterangan());
                    Employee pembuatTemuan = employeeRestService.getById(temuanRisikoData.getIdPembuat());
                    temuanRisiko.setPembuat(pembuatTemuan);
                    temuanRisikoRestService.buatTemuanRisiko(temuanRisiko);
                }

            if (komponenPemeriksaanData.getDaftarRekomendasi() != null)
                for (RekomendasiDTO rekomendasiData: komponenPemeriksaanData.getDaftarRekomendasi()) {
                    Rekomendasi rekomendasi = new Rekomendasi();
                    rekomendasi.setKomponenPemeriksaan(komponenPemeriksaan);
                    rekomendasi.setKeterangan(rekomendasiData.getKeterangan());
                    rekomendasi.setStatusRekomendasi(statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getStatus()));
                    Employee pembuatRekomendasi = employeeRestService.getById(rekomendasiData.getIdPembuat());
                    rekomendasi.setPembuat(pembuatRekomendasi);
                    rekomendasiRestService.buatRekomendasi(rekomendasi);
                }
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(hasilPemeriksaan);

        return response;
    }

    /**
     * Mengubah  hasil pemeriksaan
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang akan diubah
     * @return hasil pemeriksaan yang telah disimpan perubahannya
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<HasilPemeriksaan> ubahHasilPemeriksaan(
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO
    ) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();
        HasilPemeriksaan hasilPemeriksaanTemp = hasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getId());
        hasilPemeriksaanTemp.setStatusHasilPemeriksaan(
                statusHasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getStatus()));
        HasilPemeriksaan hasilPemeriksaan =
                hasilPemeriksaanRestService.buatHasilPemeriksaan(hasilPemeriksaanDTO.getId(), hasilPemeriksaanTemp);

        for (KomponenPemeriksaanDTO komponenPemeriksaanData: hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
            KomponenPemeriksaan komponenPemeriksaanTemp = komponenPemeriksaanRestService.getById(komponenPemeriksaanData.getId());
            if (komponenPemeriksaanData.getIdRiskLevel() != null) {
                komponenPemeriksaanTemp.setRiskLevel(riskLevelRestService.getById(komponenPemeriksaanData.getIdRiskLevel()));
            }
            if (komponenPemeriksaanData.getJumlahSampel() != null) {
                komponenPemeriksaanTemp.setJumlahSampel(komponenPemeriksaanData.getJumlahSampel());
            }
            if (komponenPemeriksaanData.getKeteranganSampel() != null) {
                komponenPemeriksaanTemp.setKeteranganSampel(komponenPemeriksaanData.getKeteranganSampel());
            }
            KomponenPemeriksaan komponenPemeriksaan =
                    komponenPemeriksaanRestService.ubahKomponenPemeriksaan(komponenPemeriksaanData.getId(), komponenPemeriksaanTemp);

            List<Integer> daftarTemuanRisikoTerdaftar = new ArrayList<Integer>();
            daftarTemuanRisikoTerdaftar.add(-1);
            List<TemuanRisiko> temuanRisikoTargetHapus =
                    temuanRisikoRestService.getByKomponenPemeriksaan(komponenPemeriksaan);
            if(komponenPemeriksaanData.getDaftarTemuanRisiko() != null)
                for (TemuanRisikoDTO temuanRisikoData: komponenPemeriksaanData.getDaftarTemuanRisiko()) {
                    if (temuanRisikoData.getId() == null) {
                        TemuanRisiko temuanRisiko = new TemuanRisiko();
                        temuanRisiko.setKomponenPemeriksaan(komponenPemeriksaan);
                        temuanRisiko.setKeterangan(temuanRisikoData.getKeterangan());
                        Employee pembuatTemuan = employeeRestService.getById(temuanRisikoData.getIdPembuat());
                        temuanRisiko.setPembuat(pembuatTemuan);
                        temuanRisikoRestService.buatTemuanRisiko(temuanRisiko);
                    } else {
                        TemuanRisiko temuanRisiko = temuanRisikoRestService.getById(temuanRisikoData.getId());
                        temuanRisiko.setKeterangan(temuanRisikoData.getKeterangan());
                        temuanRisikoRestService.ubahTemuanRisiko(temuanRisikoData.getId(), temuanRisiko);
                        daftarTemuanRisikoTerdaftar.add(temuanRisikoData.getId());
                    }
                }
            for (TemuanRisiko temuanRisikoHapus: temuanRisikoTargetHapus) {
                boolean hapusTemuanRisiko = true;
                for (Integer temuanRisikoTerdaftar: daftarTemuanRisikoTerdaftar) {
                    if (temuanRisikoHapus.getIdTemuanRisiko() == temuanRisikoTerdaftar) hapusTemuanRisiko = false;
                }
                if (hapusTemuanRisiko) temuanRisikoRestService.hapusTemuanRisiko(temuanRisikoHapus.getIdTemuanRisiko());
            }

            List<Integer> daftarRekomendasiTerdaftar = new ArrayList<Integer>();
            daftarRekomendasiTerdaftar.add(-1);
            List<Rekomendasi> rekomendasiTargetHapus =
                    rekomendasiRestService.getByKomponenPemeriksaan(komponenPemeriksaan);
            if (komponenPemeriksaanData.getDaftarRekomendasi() != null)
                for (RekomendasiDTO rekomendasiData: komponenPemeriksaanData.getDaftarRekomendasi()) {
                    if (rekomendasiData.getId() == null) {
                        Rekomendasi rekomendasi = new Rekomendasi();
                        rekomendasi.setKomponenPemeriksaan(komponenPemeriksaan);
                        rekomendasi.setKeterangan(rekomendasiData.getKeterangan());
                        rekomendasi.setStatusRekomendasi(statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getStatus()));
                        Employee pembuatRekomendasi = employeeRestService.getById(rekomendasiData.getIdPembuat());
                        rekomendasi.setPembuat(pembuatRekomendasi);
                        rekomendasiRestService.buatRekomendasi(rekomendasi);
                    } else {
                        Rekomendasi rekomendasi = rekomendasiRestService.getById(rekomendasiData.getId());
                        rekomendasi.setKeterangan(rekomendasiData.getKeterangan());
                        rekomendasi.setStatusRekomendasi(statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getStatus()));
                        rekomendasiRestService.ubahRekomendasi(rekomendasiData.getId(), rekomendasi);
                        daftarRekomendasiTerdaftar.add(rekomendasiData.getId());
                    }
                }
            for (Rekomendasi rekomendasiHapus: rekomendasiTargetHapus) {
                boolean hapusRekomendasi = true;
                for (Integer rekomendasiTerdaftar: daftarRekomendasiTerdaftar) {
                    if (rekomendasiHapus.getIdRekomendasi() == rekomendasiTerdaftar) hapusRekomendasi = false;
                }
                if (hapusRekomendasi) rekomendasiRestService.hapusRekomendasi(rekomendasiHapus.getIdRekomendasi());
            }
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(hasilPemeriksaan);

        return response;
    }

    /**
     * Menghapus hasil pemeriksaan
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang akan dihapus
     */
    @DeleteMapping("/hapus")
    private BaseResponse<String> hapusHasilPemeriksaan(
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaanDTO.getId());

            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Reminder dengan id " + hasilPemeriksaanDTO.getId() + " terhapus!");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Reminder dengan id " + hasilPemeriksaanDTO.getId() + " tidak dapat ditemukan");
        }
        return response;
    }

    /**
     * Menyetujui atai menolak hasil pemeriksaan
     *
     * @param persetujuanHasilPemeriksaanDTO data transfer object untuk persetujuan hasil pemeriksaan
     */
    @PutMapping(value = "/persetujuan", consumes = {"application/json"})
    private BaseResponse<String> persetujuanHasilPemeriksaan(
            @RequestBody PersetujuanHasilPemeriksaanDTO persetujuanHasilPemeriksaanDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            HasilPemeriksaan hasilPemeriksaanTemp = hasilPemeriksaanRestService.getById(
                    persetujuanHasilPemeriksaanDTO.getIdHasilPemeriksaan());
            hasilPemeriksaanTemp.setStatusHasilPemeriksaan(statusHasilPemeriksaanRestService.getById(
                    persetujuanHasilPemeriksaanDTO.getStatus()));
            hasilPemeriksaanTemp.setPemeriksa(employeeRestService.getById(
                    persetujuanHasilPemeriksaanDTO.getIdPemeriksa()));
            hasilPemeriksaanTemp.setFeedback(persetujuanHasilPemeriksaanDTO.getFeedback());
            HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.buatHasilPemeriksaan(
                    persetujuanHasilPemeriksaanDTO.getIdHasilPemeriksaan(), hasilPemeriksaanTemp);

            for (KomponenPemeriksaan komponenPemeriksaan:
                    komponenPemeriksaanRestService.getByHasilPemeriksaan(hasilPemeriksaan)) {
                for (Rekomendasi rekomendasi: rekomendasiRestService.getByKomponenPemeriksaan(komponenPemeriksaan)) {
                    rekomendasi.setStatusRekomendasi(statusRekomendasiRestService.getById(
                            persetujuanHasilPemeriksaanDTO.getStatus()));
                    rekomendasiRestService.ubahRekomendasi(rekomendasi.getIdRekomendasi(), rekomendasi);
                }
            }

            if (persetujuanHasilPemeriksaanDTO.getStatus() == 3) {
                response.setResult("Hasil Pemeriksaan dengan id " +
                        persetujuanHasilPemeriksaanDTO.getIdHasilPemeriksaan() + " ditolak!");
            } else {
                response.setResult("Hasil Pemeriksaan dengan id " +
                        persetujuanHasilPemeriksaanDTO.getIdHasilPemeriksaan() + " disetujui!");
            }
            response.setStatus(200);
            response.setMessage("success");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Hasil Pemeriksaan dengan id " +
                    persetujuanHasilPemeriksaanDTO.getIdHasilPemeriksaan() + " tidak dapat ditemukan");
        }
        return response;
    }
}
