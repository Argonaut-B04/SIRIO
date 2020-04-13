package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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

    @Autowired
    private RoleRestService roleRestService;

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
     * Mengambil tabel daftar hasil pemeriksaan
     *
     * @return tabel daftar hasil pemeriksaan
     */
    @GetMapping("/getTabelHasilPemeriksaan")
    private BaseResponse<List<HasilPemeriksaanDTO>> getTabelHasilPemeriksaan(
            Principal principal, ModelMap model
    ) {
        BaseResponse<List<HasilPemeriksaanDTO>> response = new BaseResponse<>();
        Employee employee = employeeRestService.getByUsername(principal.getName()).get();

        List<HasilPemeriksaan> daftarHasilPemeriksaan = employee.getRole() == roleRestService.getById(6) ?
                hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(
                        tugasPemeriksaanRestService.getByPelaksana(employee)) : hasilPemeriksaanRestService.getAll();

        List<HasilPemeriksaanDTO> result = new ArrayList<>();
        for (HasilPemeriksaan hasilPemeriksaan: daftarHasilPemeriksaan) {
            HasilPemeriksaanDTO hasilPemeriksaanDTO = new HasilPemeriksaanDTO();
            hasilPemeriksaanDTO.setId(hasilPemeriksaan.getIdHasilPemeriksaan());
            hasilPemeriksaanDTO.setTugasPemeriksaan(new TugasPemeriksaanDTO());
            hasilPemeriksaanDTO.getTugasPemeriksaan().setId(hasilPemeriksaan.getTugasPemeriksaan().getIdTugas());
            hasilPemeriksaanDTO.getTugasPemeriksaan().setNamaKantorCabang(
                    hasilPemeriksaan.getTugasPemeriksaan().getKantorCabang().getNamaKantor());
            hasilPemeriksaanDTO.setNamaStatus(hasilPemeriksaan.getStatusHasilPemeriksaan().getNamaStatus());
            List<Rekomendasi> daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(
                    komponenPemeriksaanRestService.getByHasilPemeriksaan(hasilPemeriksaan));
            hasilPemeriksaanDTO.setSiapDijalankan(true);
            for (Rekomendasi rekomendasi: daftarRekomendasi) {
                if (rekomendasi.getStatusRekomendasi() != statusRekomendasiRestService.getById(5))
                    hasilPemeriksaanDTO.setSiapDijalankan(false);
            }
            result.add(hasilPemeriksaanDTO);
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);

        return response;
    }

    /**
     * Mengambil suatu hasil pemeriksaan
     *
     * @param idHasilPemeriksaan identifier hasil pemeriksaan
     * @return detail hasil pemeriksaan
     */
    @GetMapping("/{idHasilPemeriksaan}")
    private BaseResponse<HasilPemeriksaanDTO> getDetailHasilPemeriksaan(
            @PathVariable("idHasilPemeriksaan") int idHasilPemeriksaan
    ) {
        BaseResponse<HasilPemeriksaanDTO> response = new BaseResponse<>();
        try {
            HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.getById(idHasilPemeriksaan);
            HasilPemeriksaanDTO result = new HasilPemeriksaanDTO();
            result.setId(hasilPemeriksaan.getIdHasilPemeriksaan());
            result.setIdStatus(hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil());
            result.setFeedback(hasilPemeriksaan.getFeedback());
            result.setTugasPemeriksaan(new TugasPemeriksaanDTO());
            result.getTugasPemeriksaan().setId(hasilPemeriksaan.getTugasPemeriksaan().getIdTugas());
            result.getTugasPemeriksaan().setIdQA(hasilPemeriksaan.getTugasPemeriksaan().getPelaksana().getIdEmployee());
            result.getTugasPemeriksaan().setNamaQA(hasilPemeriksaan.getTugasPemeriksaan().getPelaksana().getNama());
            result.getTugasPemeriksaan().setUsernameQA(hasilPemeriksaan.getTugasPemeriksaan().getPelaksana().getUsername());
            result.getTugasPemeriksaan().setNamaKantorCabang(
                    hasilPemeriksaan.getTugasPemeriksaan().getKantorCabang().getNamaKantor());
            result.setNamaStatus(hasilPemeriksaan.getStatusHasilPemeriksaan().getNamaStatus());
            result.setNamaPembuat(hasilPemeriksaan.getPembuat().getNama());
            result.setUsernamePembuat(hasilPemeriksaan.getPembuat().getUsername());
            if (hasilPemeriksaan.getPemeriksa() != null) result.setNamaPemeriksa(hasilPemeriksaan.getPemeriksa().getNama());

            result.setDaftarKomponenPemeriksaan(new ArrayList<>());
            for (KomponenPemeriksaan komponenPemeriksaan:
                    komponenPemeriksaanRestService.getByHasilPemeriksaan(hasilPemeriksaan)) {
                KomponenPemeriksaanDTO komponenPemeriksaanDTO = new KomponenPemeriksaanDTO();
                komponenPemeriksaanDTO.setId(komponenPemeriksaan.getIdKomponenPemeriksaan());
                komponenPemeriksaanDTO.setIdRiskLevel(komponenPemeriksaan.getRiskLevel().getIdLevel());
                komponenPemeriksaanDTO.setNamaRiskLevel(komponenPemeriksaan.getRiskLevel().getNamaLevel());
                komponenPemeriksaanDTO.setBobotRiskLevel(komponenPemeriksaan.getRiskLevel().getBobotLevel());
                komponenPemeriksaanDTO.setJumlahSampel(komponenPemeriksaan.getJumlahSampel());
                komponenPemeriksaanDTO.setKeteranganSampel(komponenPemeriksaan.getKeteranganSampel());

                komponenPemeriksaanDTO.setRisiko(new RisikoDTO());
                komponenPemeriksaanDTO.getRisiko().setId(komponenPemeriksaan.getRisiko().getIdRisiko());
                komponenPemeriksaanDTO.getRisiko().setKomponen(komponenPemeriksaan.getRisiko().getKomponen());
                komponenPemeriksaanDTO.getRisiko().setNama(komponenPemeriksaan.getRisiko().getNamaRisiko());
                komponenPemeriksaanDTO.getRisiko().setNamaSop(komponenPemeriksaan.getRisiko().getSop().getJudul());
                komponenPemeriksaanDTO.getRisiko().setLinkSop(komponenPemeriksaan.getRisiko().getSop().getLinkDokumen());
                if(komponenPemeriksaan.getRisiko().getParent() != null) {
                    komponenPemeriksaanDTO.getRisiko().setParent(komponenPemeriksaan.getRisiko().getParent().getIdRisiko());
                    if(komponenPemeriksaan.getRisiko().getParent().getParent() != null)
                        komponenPemeriksaanDTO.getRisiko().setGrantParent(komponenPemeriksaan.getRisiko().getParent().getParent().getIdRisiko());
                }
                komponenPemeriksaanDTO.setDaftarRekomendasiTerdaftar(
                        rekomendasiRestService.getByKomponenPemeriksaan(komponenPemeriksaan));
                komponenPemeriksaanDTO.setDaftarTemuanRisikoTerdaftar(
                        temuanRisikoRestService.getByKomponenPemeriksaan(komponenPemeriksaan));
                result.getDaftarKomponenPemeriksaan().add(komponenPemeriksaanDTO);
            }

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
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
            Principal principal, ModelMap model
    ) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();

        Employee employee = employeeRestService.getByUsername(principal.getName()).get();
        HasilPemeriksaan hasilPemeriksaanTemp = new HasilPemeriksaan();

        try {
            StatusHasilPemeriksaan statusHasilPemeriksaan =
                    statusHasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getIdStatus());
            if (statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(1) &&
                    statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(2))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Status tidak diperbolehkan!"
                );
            hasilPemeriksaanTemp.setStatusHasilPemeriksaan(statusHasilPemeriksaan);
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Status Hasil Pemeriksaan tidak ditemukan!"
            );
        }

        hasilPemeriksaanTemp.setPembuat(employee);

        TugasPemeriksaan tugasPemeriksaan;
        try {
            tugasPemeriksaan = tugasPemeriksaanRestService.getById(
                    hasilPemeriksaanDTO.getTugasPemeriksaan().getId());
            if (hasilPemeriksaanRestService.getByTugasPemeriksaan(tugasPemeriksaan).isPresent())
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Hasil pemeriksaan untuk tugas pemeriksaan dengan ID " +
                        tugasPemeriksaan.getIdTugas() + "sudah tersimpan pada database!"
                );
            hasilPemeriksaanTemp.setTugasPemeriksaan(tugasPemeriksaan);
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tugas Pemeriksaan tidak ditemukan!"
            );
        }

        if (employee != tugasPemeriksaan.getPelaksana() && employee.getRole() == roleRestService.getById(6))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee() +
                    " tidak ditugaskan untuk membuat hasil pemeriksaan ini!"
            );

        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.buatHasilPemeriksaan(hasilPemeriksaanTemp);

        if(hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan() != null &&
                !hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan().isEmpty())
            for (KomponenPemeriksaanDTO komponenPemeriksaanData: hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
                KomponenPemeriksaan komponenPemeriksaanTemp = new KomponenPemeriksaan();
                komponenPemeriksaanTemp.setHasilPemeriksaan(hasilPemeriksaan);
                try {
                    Risiko risiko = risikoRestService.getById(komponenPemeriksaanData.getRisiko().getId());
                    if (risiko.getRisikoKategori() != 3) {
                        hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Risiko harus kategori 3!");
                    }
                    komponenPemeriksaanTemp.setRisiko(risiko);
                } catch (NoSuchElementException | NullPointerException e) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Risiko tidak ditemukan!"
                    );
                }

                if (komponenPemeriksaanData.getIdRiskLevel() != null) {
                    try {
                        komponenPemeriksaanTemp.setRiskLevel(riskLevelRestService.getById(komponenPemeriksaanData.getIdRiskLevel()));
                    } catch (NoSuchElementException | NullPointerException e) {
                        hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Risk Level tidak ditemukan!"
                        );
                    }
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(
                            HttpStatus.METHOD_NOT_ALLOWED, "Risk Level perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!"
                    );
                }

                if (komponenPemeriksaanData.getJumlahSampel() != null) {
                    komponenPemeriksaanTemp.setJumlahSampel(komponenPemeriksaanData.getJumlahSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(
                            HttpStatus.METHOD_NOT_ALLOWED, "Jumlah sampel perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!"
                    );
                }

                if (komponenPemeriksaanData.getKeteranganSampel() != null) {
                    komponenPemeriksaanTemp.setKeteranganSampel(komponenPemeriksaanData.getKeteranganSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(
                            HttpStatus.METHOD_NOT_ALLOWED, "Keterangan sampel perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!"
                    );
                }

                KomponenPemeriksaan komponenPemeriksaan =
                        komponenPemeriksaanRestService.buatKomponenPemeriksaan(komponenPemeriksaanTemp);

                createTemuanRisikoAndRekomendasi(
                        hasilPemeriksaanDTO, employee, komponenPemeriksaanData, komponenPemeriksaan);
            }
        else {
            hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Komponen Pemeriksaan tidak ditemukan!"
            );
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
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
            Principal principal, ModelMap model
    ) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();
        Employee employee = employeeRestService.getByUsername(principal.getName()).get();

        HasilPemeriksaan hasilPemeriksaanTemp;
        try {
            hasilPemeriksaanTemp = hasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getId());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Hasil Pemeriksaan tidak ditemukan!"
            );
        }

        try {
            StatusHasilPemeriksaan statusHasilPemeriksaan =
                    statusHasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getIdStatus());
            if ((hasilPemeriksaanTemp.getStatusHasilPemeriksaan() != statusHasilPemeriksaanRestService.getById(1) &&
                    hasilPemeriksaanTemp.getStatusHasilPemeriksaan() != statusHasilPemeriksaanRestService.getById(2) &&
                    hasilPemeriksaanTemp.getStatusHasilPemeriksaan() != statusHasilPemeriksaanRestService.getById(3)) ||
                    (statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(1) &&
                    statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(2) &&
                    statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(3)))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Status tidak diperbolehkan!"
                );
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Status Hasil Pemeriksaan tidak ditemukan!"
            );
        }

        if (employee != hasilPemeriksaanTemp.getTugasPemeriksaan().getPelaksana() &&
                employee.getRole() == roleRestService.getById(6))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee() +
                    " tidak ditugaskan untuk mengubah hasil pemeriksaan ini!"
            );

        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.buatHasilPemeriksaan(
                hasilPemeriksaanDTO.getId(), hasilPemeriksaanTemp);

        if(hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan() != null &&
                !hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan().isEmpty())
            for (KomponenPemeriksaanDTO komponenPemeriksaanData: hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
                KomponenPemeriksaan komponenPemeriksaanTemp;
                try {
                    komponenPemeriksaanTemp = komponenPemeriksaanRestService.getById(komponenPemeriksaanData.getId());
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Komponen Pemeriksaan dengan ID " + komponenPemeriksaanData.getId() +
                            " tidak ditemukan! Hanya data valid sebelumnya yang berhasil diubah"
                    );
                }

                if (komponenPemeriksaanData.getIdRiskLevel() != null) {
                    try {
                        komponenPemeriksaanTemp.setRiskLevel(riskLevelRestService.getById(komponenPemeriksaanData.getIdRiskLevel()));
                    } catch (NoSuchElementException | NullPointerException e) {
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Risk level untuk Komponen Pemeriksaan dengan ID " +
                                komponenPemeriksaanData.getId() +
                                " tidak ditemukan! Hanya data valid sebelumnya yang berhasil diubah"
                        );
                    }
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(
                            HttpStatus.METHOD_NOT_ALLOWED, "Risk Level perlu diisi untuk pengajuan persetujuan " +
                            "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah"
                    );
                }

                if (komponenPemeriksaanData.getJumlahSampel() != null) {
                    komponenPemeriksaanTemp.setJumlahSampel(komponenPemeriksaanData.getJumlahSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(
                            HttpStatus.METHOD_NOT_ALLOWED, "Jumlah sampel perlu diisi untuk pengajuan persetujuan " +
                            "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah"
                    );
                }

                if (komponenPemeriksaanData.getKeteranganSampel() != null) {
                    komponenPemeriksaanTemp.setKeteranganSampel(komponenPemeriksaanData.getKeteranganSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(
                            HttpStatus.METHOD_NOT_ALLOWED, "Keterangan sampel perlu diisi untuk pengajuan persetujuan " +
                            "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah"
                    );
                }
                komponenPemeriksaanRestService.ubahKomponenPemeriksaan(
                        komponenPemeriksaanData.getId(), komponenPemeriksaanTemp);
            }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Komponen Pemeriksaan tidak ditemukan!"
            );
        }

        hasilPemeriksaan = hasilPemeriksaanRestService.ubahStatus(
                hasilPemeriksaan.getIdHasilPemeriksaan(), hasilPemeriksaanDTO.getIdStatus());
        for (KomponenPemeriksaanDTO komponenPemeriksaanData: hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
            KomponenPemeriksaan komponenPemeriksaan =
                    komponenPemeriksaanRestService.getById(komponenPemeriksaanData.getId());

            List<TemuanRisiko> daftarTemuanRisikoTersimpan =
                    temuanRisikoRestService.getByKomponenPemeriksaan(komponenPemeriksaan);
            if (!daftarTemuanRisikoTersimpan.isEmpty()) {
                for (TemuanRisiko temuanRisikoTersimpan: daftarTemuanRisikoTersimpan) {
                    boolean hapusTemuanRisiko = true;
                    if (komponenPemeriksaanData.getDaftarTemuanRisikoTerdaftar() != null &&
                            !komponenPemeriksaanData.getDaftarTemuanRisikoTerdaftar().isEmpty()) {
                        for(TemuanRisiko temuanRisikoTerdaftar: komponenPemeriksaanData.getDaftarTemuanRisikoTerdaftar()) {
                            if (temuanRisikoTerdaftar.getKeterangan() != null &&
                                    !temuanRisikoTerdaftar.getKeterangan().equals("") &&
                                    temuanRisikoTersimpan == temuanRisikoRestService.getById(
                                            temuanRisikoTerdaftar.getIdTemuanRisiko())) {
                                hapusTemuanRisiko = false;
                                temuanRisikoTersimpan.setKeterangan(temuanRisikoTerdaftar.getKeterangan());
                                temuanRisikoRestService.ubahTemuanRisiko(
                                        temuanRisikoTersimpan.getIdTemuanRisiko(), temuanRisikoTersimpan);
                            }
                        }
                        if (hapusTemuanRisiko) temuanRisikoRestService.hapusTemuanRisiko(temuanRisikoTersimpan.getIdTemuanRisiko());
                    } else {
                        temuanRisikoRestService.hapusTemuanRisiko(temuanRisikoTersimpan.getIdTemuanRisiko());
                    }
                }
            }

            List<Rekomendasi> daftarRekomendasiTersimpan =
                    rekomendasiRestService.getByKomponenPemeriksaan(komponenPemeriksaan);
            if (!daftarRekomendasiTersimpan.isEmpty()) {
                for (Rekomendasi rekomendasiTersimpan: daftarRekomendasiTersimpan) {
                    boolean hapusRekomendasi = true;
                    if (komponenPemeriksaanData.getDaftarRekomendasiTerdaftar() != null &&
                            !komponenPemeriksaanData.getDaftarRekomendasiTerdaftar().isEmpty()) {
                        for(Rekomendasi rekomendasiTerdaftar: komponenPemeriksaanData.getDaftarRekomendasiTerdaftar()) {
                            if (rekomendasiTerdaftar.getKeterangan() != null &&
                                    !rekomendasiTerdaftar.getKeterangan().equals("") &&
                                    rekomendasiTersimpan == rekomendasiRestService.getById(
                                            rekomendasiTerdaftar.getIdRekomendasi())) {
                                hapusRekomendasi = false;
                                rekomendasiTersimpan.setKeterangan(rekomendasiTerdaftar.getKeterangan());
                                rekomendasiTersimpan.setStatusRekomendasi(
                                        statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getIdStatus()));
                                rekomendasiRestService.ubahRekomendasi(
                                        rekomendasiTersimpan.getIdRekomendasi(), rekomendasiTersimpan);
                            }
                        }
                        if (hapusRekomendasi) rekomendasiRestService.hapusRekomendasi(rekomendasiTersimpan.getIdRekomendasi());
                    } else {
                        rekomendasiRestService.hapusRekomendasi(rekomendasiTersimpan.getIdRekomendasi());
                    }
                }
            }

            createTemuanRisikoAndRekomendasi(
                    hasilPemeriksaanDTO, employee, komponenPemeriksaanData, komponenPemeriksaan);

        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(hasilPemeriksaan);

        return response;
    }

    private void createTemuanRisikoAndRekomendasi(
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO, Employee employee,
            KomponenPemeriksaanDTO komponenPemeriksaanData, KomponenPemeriksaan komponenPemeriksaan
    ) {
        if (komponenPemeriksaanData.getDaftarTemuanRisiko() != null &&
                !komponenPemeriksaanData.getDaftarTemuanRisiko().isEmpty())
            for (TemuanRisikoDTO temuanRisikoData : komponenPemeriksaanData.getDaftarTemuanRisiko()) {
                if (temuanRisikoData.getKeterangan() != null && !temuanRisikoData.getKeterangan().equals("")) {
                    TemuanRisiko temuanRisiko = new TemuanRisiko();
                    temuanRisiko.setKomponenPemeriksaan(komponenPemeriksaan);
                    temuanRisiko.setKeterangan(temuanRisikoData.getKeterangan());
                    temuanRisiko.setPembuat(employee);
                    temuanRisikoRestService.buatTemuanRisiko(temuanRisiko);
                }
            }

        if (komponenPemeriksaanData.getDaftarRekomendasi() != null &&
                !komponenPemeriksaanData.getDaftarRekomendasi().isEmpty())
            for (RekomendasiDTO rekomendasiData : komponenPemeriksaanData.getDaftarRekomendasi()) {
                if (rekomendasiData.getKeterangan() != null && !rekomendasiData.getKeterangan().equals("")) {
                    Rekomendasi rekomendasi = new Rekomendasi();
                    rekomendasi.setKomponenPemeriksaan(komponenPemeriksaan);
                    rekomendasi.setKeterangan(rekomendasiData.getKeterangan());
                    rekomendasi.setStatusRekomendasi(statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getIdStatus()));
                    rekomendasi.setPembuat(employee);
                    rekomendasiRestService.buatRekomendasi(rekomendasi);
                }
            }
    }

    /**
     * Menghapus hasil pemeriksaan
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusHasilPemeriksaan(
            @RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
            Principal principal, ModelMap model
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        Employee employee = employeeRestService.getByUsername(principal.getName()).get();

        HasilPemeriksaan hasilPemeriksaan;
        try {
            hasilPemeriksaan = hasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getId());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Hasil Pemeriksaan tidak ditemukan!"
            );
        }

        if (hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil() != 1 &&
                hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil() != 2 &&
                hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil() != 3)
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Hasil Pemeriksaan tidak boleh dihapus!"
            );

        if (employee != hasilPemeriksaan.getTugasPemeriksaan().getPelaksana() &&
                employee.getRole() == roleRestService.getById(6))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee() +
                    " tidak ditugaskan untuk menghapus hasil pemeriksaan ini!"
            );

        hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());

        response.setStatus(200);
        response.setMessage("success");
        response.setResult("Hasil pemeriksaan dengan id " + hasilPemeriksaanDTO.getId() + " terhapus!");
        return response;
    }

    /**
     * Menyetujui atai menolak hasil pemeriksaan
     *
     * @param persetujuanHasilPemeriksaanDTO data transfer object untuk persetujuan hasil pemeriksaan
     */
    @PostMapping(value = "/persetujuan", consumes = {"application/json"})
    private BaseResponse<String> persetujuanHasilPemeriksaan(
            @RequestBody PersetujuanHasilPemeriksaanDTO persetujuanHasilPemeriksaanDTO,
            Principal principal, ModelMap model
    ) {
        BaseResponse<String> response = new BaseResponse<>();

        Employee employee = employeeRestService.getByUsername(principal.getName()).get();

        HasilPemeriksaan hasilPemeriksaanTemp;
        try {
            hasilPemeriksaanTemp = hasilPemeriksaanRestService.getById(persetujuanHasilPemeriksaanDTO.getId());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Hasil Pemeriksaan tidak ditemukan!"
            );
        }

        try {
            StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanRestService.getById(
                    persetujuanHasilPemeriksaanDTO.getStatus());
            if (hasilPemeriksaanTemp.getStatusHasilPemeriksaan().getIdStatusHasil() != 2 ||
                    (statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(3) &&
                    statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(4)))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Pengajuan persetujuan tidak diperbolehkan!"
                );
            hasilPemeriksaanTemp.setStatusHasilPemeriksaan(statusHasilPemeriksaan);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Status Hasil Pemeriksaan tidak ditemukan!"
            );
        }

        if (persetujuanHasilPemeriksaanDTO.getStatus() == 3 && (persetujuanHasilPemeriksaanDTO.getFeedback() == null ||
                persetujuanHasilPemeriksaanDTO.getFeedback().equals("")))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Feedback perlu diisi untuk penolakan Hasil Pemeriksaan!"
            );


        hasilPemeriksaanTemp.setPemeriksa(employee);
        hasilPemeriksaanTemp.setFeedback(persetujuanHasilPemeriksaanDTO.getFeedback());
        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.buatHasilPemeriksaan(
                persetujuanHasilPemeriksaanDTO.getId(), hasilPemeriksaanTemp);

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
                    persetujuanHasilPemeriksaanDTO.getId() + " ditolak!");
        } else {
            response.setResult("Hasil Pemeriksaan dengan id " +
                    persetujuanHasilPemeriksaanDTO.getId() + " disetujui!");
        }
        response.setStatus(200);
        response.setMessage("success");

        return response;
    }
}
