package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Rekomendasi;
import com.argonautb04.sirio.model.Risiko;
import com.argonautb04.sirio.model.StatusHasilPemeriksaan;
import com.argonautb04.sirio.model.TemuanRisiko;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.rest.HasilPemeriksaanDTO;
import com.argonautb04.sirio.rest.KomponenPemeriksaanDTO;
import com.argonautb04.sirio.rest.PersetujuanHasilPemeriksaanDTO;
import com.argonautb04.sirio.rest.RekomendasiDTO;
import com.argonautb04.sirio.rest.RisikoDTO;
import com.argonautb04.sirio.rest.TemuanRisikoDTO;
import com.argonautb04.sirio.rest.TugasPemeriksaanDTO;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.HasilPemeriksaanRestService;
import com.argonautb04.sirio.services.KomponenPemeriksaanRestService;
import com.argonautb04.sirio.services.RekomendasiRestService;
import com.argonautb04.sirio.services.RisikoRestService;
import com.argonautb04.sirio.services.RiskLevelRestService;
import com.argonautb04.sirio.services.RoleRestService;
import com.argonautb04.sirio.services.StatusHasilPemeriksaanRestService;
import com.argonautb04.sirio.services.StatusRekomendasiRestService;
import com.argonautb04.sirio.services.TemuanRisikoRestService;
import com.argonautb04.sirio.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        List<HasilPemeriksaan> result = hasilPemeriksaanRestService.getAll();
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil seluruh hasil pemeriksaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar hasil pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<HasilPemeriksaan>> getAllHasilPemeriksaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat) {
        Employee pembuat = employeeRestService.validateEmployeeExistById(idPembuat);
        List<HasilPemeriksaan> result = hasilPemeriksaanRestService.getByPembuat(pembuat);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil tabel daftar hasil pemeriksaan
     *
     * @return tabel daftar hasil pemeriksaan
     */
    @GetMapping("/getTabelHasilPemeriksaan")
    private BaseResponse<List<HasilPemeriksaanDTO>> getTabelHasilPemeriksaan(Principal principal) {
        BaseResponse<List<HasilPemeriksaanDTO>> response = new BaseResponse<>();
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        List<HasilPemeriksaan> daftarHasilPemeriksaan = employee.getRole() == roleRestService.getById(5)
                ? hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(
                tugasPemeriksaanRestService.getByPelaksana(employee))
                : hasilPemeriksaanRestService.getAll();

        List<HasilPemeriksaanDTO> result = new ArrayList<>();
        for (HasilPemeriksaan hasilPemeriksaan : daftarHasilPemeriksaan) {
            HasilPemeriksaanDTO hasilPemeriksaanDTO = new HasilPemeriksaanDTO();
            hasilPemeriksaanDTO.setId(hasilPemeriksaan.getIdHasilPemeriksaan());
            hasilPemeriksaanDTO.setTugasPemeriksaan(new TugasPemeriksaanDTO());
            hasilPemeriksaanDTO.getTugasPemeriksaan().setId(hasilPemeriksaan.getTugasPemeriksaan().getIdTugas());
            hasilPemeriksaanDTO.getTugasPemeriksaan()
                    .setNamaKantorCabang(hasilPemeriksaan.getTugasPemeriksaan().getKantorCabang().getNamaKantor());
            hasilPemeriksaanDTO.setNamaStatus(hasilPemeriksaan.getStatusHasilPemeriksaan().getNamaStatus());
            List<Rekomendasi> daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(
                    komponenPemeriksaanRestService.getByHasilPemeriksaan(hasilPemeriksaan));
            hasilPemeriksaanDTO.setSiapDijalankan(true);
            for (Rekomendasi rekomendasi : daftarRekomendasi) {
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
            @PathVariable("idHasilPemeriksaan") int idHasilPemeriksaan) {
        BaseResponse<HasilPemeriksaanDTO> response = new BaseResponse<>();
        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.validateExistById(idHasilPemeriksaan);

        HasilPemeriksaanDTO result = new HasilPemeriksaanDTO();
        result.setId(hasilPemeriksaan.getIdHasilPemeriksaan());
        result.setIdStatus(hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil());
        result.setFeedback(hasilPemeriksaan.getFeedback());
        result.setTugasPemeriksaan(new TugasPemeriksaanDTO());
        result.getTugasPemeriksaan().setId(hasilPemeriksaan.getTugasPemeriksaan().getIdTugas());
        result.getTugasPemeriksaan().setIdQA(hasilPemeriksaan.getTugasPemeriksaan().getPelaksana().getIdEmployee());
        result.getTugasPemeriksaan().setNamaQA(hasilPemeriksaan.getTugasPemeriksaan().getPelaksana().getNama());
        result.getTugasPemeriksaan().setUsernameQA(hasilPemeriksaan.getTugasPemeriksaan().getPelaksana().getUsername());
        result.getTugasPemeriksaan()
                .setNamaKantorCabang(hasilPemeriksaan.getTugasPemeriksaan().getKantorCabang().getNamaKantor());
        result.setNamaStatus(hasilPemeriksaan.getStatusHasilPemeriksaan().getNamaStatus());
        result.setNamaPembuat(hasilPemeriksaan.getPembuat().getNama());
        result.setUsernamePembuat(hasilPemeriksaan.getPembuat().getUsername());
        if (hasilPemeriksaan.getPemeriksa() != null)
            result.setNamaPemeriksa(hasilPemeriksaan.getPemeriksa().getNama());

        result.setDaftarKomponenPemeriksaan(new ArrayList<>());
        for (KomponenPemeriksaan komponenPemeriksaan : komponenPemeriksaanRestService
                .getByHasilPemeriksaan(hasilPemeriksaan)) {
            KomponenPemeriksaanDTO komponenPemeriksaanDTO = new KomponenPemeriksaanDTO();
            komponenPemeriksaanDTO.setId(komponenPemeriksaan.getIdKomponenPemeriksaan());
            if (komponenPemeriksaan.getRiskLevel() != null) {
                komponenPemeriksaanDTO.setIdRiskLevel(komponenPemeriksaan.getRiskLevel().getIdLevel());
                komponenPemeriksaanDTO.setNamaRiskLevel(komponenPemeriksaan.getRiskLevel().getNamaLevel());
                komponenPemeriksaanDTO.setBobotRiskLevel(komponenPemeriksaan.getRiskLevel().getBobotLevel());
            }
            komponenPemeriksaanDTO.setJumlahSampel(komponenPemeriksaan.getJumlahSampel());
            komponenPemeriksaanDTO.setJumlahPopulasi(komponenPemeriksaan.getJumlahPopulasi());
            komponenPemeriksaanDTO.setJumlahSampelError(komponenPemeriksaan.getJumlahSampelError());
            komponenPemeriksaanDTO.setKeteranganSampel(komponenPemeriksaan.getKeteranganSampel());

            komponenPemeriksaanDTO.setRisiko(new RisikoDTO());
            komponenPemeriksaanDTO.getRisiko().setId(komponenPemeriksaan.getRisiko().getIdRisiko());
            komponenPemeriksaanDTO.getRisiko().setDetailUraian(komponenPemeriksaan.getRisiko().getDetailUraian());
            komponenPemeriksaanDTO.getRisiko().setNama(komponenPemeriksaan.getRisiko().getNamaRisiko());
            komponenPemeriksaanDTO.getRisiko().setNamaSop(komponenPemeriksaan.getRisiko().getSop().getJudul());
            komponenPemeriksaanDTO.getRisiko().setLinkSop(komponenPemeriksaan.getRisiko().getSop().getLinkDokumen());
            komponenPemeriksaanDTO.getRisiko().setDeskripsi(komponenPemeriksaan.getRisiko().getDeskripsi());
            komponenPemeriksaanDTO.getRisiko().setKetentuanSampel(komponenPemeriksaan.getRisiko().getKetentuanSampel());
            komponenPemeriksaanDTO.getRisiko().setMetodologi(komponenPemeriksaan.getRisiko().getMetodologi());
            if (komponenPemeriksaan.getRisiko().getParent() != null) {
                komponenPemeriksaanDTO.getRisiko().setParent(komponenPemeriksaan.getRisiko().getParent().getIdRisiko());
                if (komponenPemeriksaan.getRisiko().getParent().getParent() != null)
                    komponenPemeriksaanDTO.getRisiko()
                            .setGrantParent(komponenPemeriksaan.getRisiko().getParent().getParent().getIdRisiko());
            }
            komponenPemeriksaanDTO.setDaftarRekomendasiTerdaftar(new ArrayList<>());
            for (Rekomendasi rekomendasi : rekomendasiRestService.getByKomponenPemeriksaan(komponenPemeriksaan)) {
                RekomendasiDTO rekomendasiDTO = new RekomendasiDTO();
                rekomendasiDTO.setId(rekomendasi.getIdRekomendasi());
                rekomendasiDTO.setKeterangan(rekomendasi.getKeterangan());
                komponenPemeriksaanDTO.getDaftarRekomendasiTerdaftar().add(rekomendasiDTO);
            }
            komponenPemeriksaanDTO.setDaftarTemuanRisikoTerdaftar(new ArrayList<>());
            for (TemuanRisiko temuanRisiko : temuanRisikoRestService.getByKomponenPemeriksaan(komponenPemeriksaan)) {
                TemuanRisikoDTO temuanRisikoDTO = new TemuanRisikoDTO();
                temuanRisikoDTO.setId(temuanRisiko.getIdTemuanRisiko());
                temuanRisikoDTO.setKeterangan(temuanRisiko.getKeterangan());
                komponenPemeriksaanDTO.getDaftarTemuanRisikoTerdaftar().add(temuanRisikoDTO);
            }
            result.getDaftarKomponenPemeriksaan().add(komponenPemeriksaanDTO);
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Menambah hasil pemeriksaan baru untuk tugas pemeriksaan spesifik
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang
     *                            akan ditambah
     * @return hasil pemeriksaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<HasilPemeriksaan> tambahHasilPemeriksaan(@RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
                                                                  Principal principal) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();

        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        HasilPemeriksaan hasilPemeriksaanTemp = new HasilPemeriksaan();

        try {
            StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanRestService
                    .getById(hasilPemeriksaanDTO.getIdStatus());
            if (statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(1)
                    && statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(2))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Status tidak diperbolehkan!");
            hasilPemeriksaanTemp.setStatusHasilPemeriksaan(statusHasilPemeriksaan);
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status Hasil Pemeriksaan tidak ditemukan!");
        }

        hasilPemeriksaanTemp.setPembuat(employee);

        TugasPemeriksaan tugasPemeriksaan;
        try {
            tugasPemeriksaan = tugasPemeriksaanRestService.getById(hasilPemeriksaanDTO.getTugasPemeriksaan().getId());
            if (hasilPemeriksaanRestService.getByTugasPemeriksaan(tugasPemeriksaan).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Hasil pemeriksaan untuk tugas pemeriksaan dengan ID " + tugasPemeriksaan.getIdTugas()
                                + "sudah tersimpan pada database!");
            hasilPemeriksaanTemp.setTugasPemeriksaan(tugasPemeriksaan);
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tugas Pemeriksaan tidak ditemukan!");
        }

        if (employee != tugasPemeriksaan.getPelaksana() && employee.getRole() == roleRestService.getById(5))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee()
                    + " tidak ditugaskan untuk membuat hasil pemeriksaan ini!");

        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService.buatHasilPemeriksaan(hasilPemeriksaanTemp);

        if (hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan() != null
                && !hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan().isEmpty())
            for (KomponenPemeriksaanDTO komponenPemeriksaanData : hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
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
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Risiko tidak ditemukan!");
                }

                if (komponenPemeriksaanData.getIdRiskLevel() != null) {
                    try {
                        komponenPemeriksaanTemp
                                .setRiskLevel(riskLevelRestService.getById(komponenPemeriksaanData.getIdRiskLevel()));
                    } catch (NoSuchElementException | NullPointerException e) {
                        hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk Level tidak ditemukan!");
                    }
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Risk Level perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!");
                }

                if (komponenPemeriksaanData.getJumlahSampel() != null) {
                    komponenPemeriksaanTemp.setJumlahSampel(komponenPemeriksaanData.getJumlahSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Jumlah sampel perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!");
                }

                if (komponenPemeriksaanData.getJumlahPopulasi() != null) {
                    komponenPemeriksaanTemp.setJumlahPopulasi(komponenPemeriksaanData.getJumlahPopulasi());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Jumlah populasi perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!");
                }

                if (komponenPemeriksaanData.getJumlahSampelError() != null) {
                    komponenPemeriksaanTemp.setJumlahSampelError(komponenPemeriksaanData.getJumlahSampelError());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Jumlah sampel error perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!");
                }

                if (komponenPemeriksaanData.getKeteranganSampel() != null) {
                    komponenPemeriksaanTemp.setKeteranganSampel(komponenPemeriksaanData.getKeteranganSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2) {
                    hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Keterangan sampel perlu diisi untuk pengajuan persetujuan Hasil Pemeriksaan!");
                }

                KomponenPemeriksaan komponenPemeriksaan = komponenPemeriksaanRestService
                        .buatKomponenPemeriksaan(komponenPemeriksaanTemp);

                createTemuanRisikoAndRekomendasi(hasilPemeriksaanDTO, employee, komponenPemeriksaanData,
                        komponenPemeriksaan);
            }
        else {
            hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Komponen Pemeriksaan tidak ditemukan!");
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(hasilPemeriksaan);

        return response;
    }

    /**
     * Mengubah hasil pemeriksaan
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang
     *                            akan diubah
     * @return hasil pemeriksaan yang telah disimpan perubahannya
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<HasilPemeriksaan> ubahHasilPemeriksaan(@RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
                                                                Principal principal, ModelMap model) {
        BaseResponse<HasilPemeriksaan> response = new BaseResponse<>();
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        HasilPemeriksaan hasilPemeriksaanTemp = hasilPemeriksaanRestService
                .validateExistById(hasilPemeriksaanDTO.getId());

        try {
            StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanRestService
                    .getById(hasilPemeriksaanDTO.getIdStatus());
            if ((hasilPemeriksaanTemp.getStatusHasilPemeriksaan() != statusHasilPemeriksaanRestService.getById(1)
                    && hasilPemeriksaanTemp.getStatusHasilPemeriksaan() != statusHasilPemeriksaanRestService.getById(2)
                    && hasilPemeriksaanTemp.getStatusHasilPemeriksaan() != statusHasilPemeriksaanRestService.getById(3))
                    || (statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(1)
                    && statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(2)
                    && statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(3)))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Status tidak diperbolehkan!");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status Hasil Pemeriksaan tidak ditemukan!");
        }

        if (employee != hasilPemeriksaanTemp.getTugasPemeriksaan().getPelaksana()
                && employee.getRole() == roleRestService.getById(5))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee()
                    + " tidak ditugaskan untuk mengubah hasil pemeriksaan ini!");

        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService
                .buatHasilPemeriksaan(hasilPemeriksaanDTO.getId(), hasilPemeriksaanTemp);

        if (hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan() != null
                && !hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan().isEmpty())
            for (KomponenPemeriksaanDTO komponenPemeriksaanData : hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
                KomponenPemeriksaan komponenPemeriksaanTemp;
                try {
                    komponenPemeriksaanTemp = komponenPemeriksaanRestService.getById(komponenPemeriksaanData.getId());
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Komponen Pemeriksaan dengan ID " + komponenPemeriksaanData.getId()
                                    + " tidak ditemukan! Hanya data valid sebelumnya yang berhasil diubah");
                }

                if (komponenPemeriksaanData.getIdRiskLevel() != null) {
                    try {
                        komponenPemeriksaanTemp
                                .setRiskLevel(riskLevelRestService.getById(komponenPemeriksaanData.getIdRiskLevel()));
                    } catch (NoSuchElementException | NullPointerException e) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Risk level untuk Komponen Pemeriksaan dengan ID " + komponenPemeriksaanData.getId()
                                        + " tidak ditemukan! Hanya data valid sebelumnya yang berhasil diubah");
                    }
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Risk Level perlu diisi untuk pengajuan persetujuan "
                                    + "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah");
                }

                if (komponenPemeriksaanData.getJumlahSampel() != null) {
                    komponenPemeriksaanTemp.setJumlahSampel(komponenPemeriksaanData.getJumlahSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Jumlah sampel perlu diisi untuk pengajuan persetujuan "
                                    + "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah");
                }

                if (komponenPemeriksaanData.getJumlahPopulasi() != null) {
                    komponenPemeriksaanTemp.setJumlahPopulasi(komponenPemeriksaanData.getJumlahPopulasi());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Jumlah populasi perlu diisi untuk pengajuan persetujuan "
                                    + "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah");
                }

                if (komponenPemeriksaanData.getJumlahSampelError() != null) {
                    komponenPemeriksaanTemp.setJumlahSampelError(komponenPemeriksaanData.getJumlahSampelError());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Jumlah sampel error perlu diisi untuk pengajuan persetujuan "
                                    + "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah");
                }

                if (komponenPemeriksaanData.getKeteranganSampel() != null) {
                    komponenPemeriksaanTemp.setKeteranganSampel(komponenPemeriksaanData.getKeteranganSampel());
                } else if (hasilPemeriksaanDTO.getIdStatus() == 2 || hasilPemeriksaanDTO.getIdStatus() == 3) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                            "Keterangan sampel perlu diisi untuk pengajuan persetujuan "
                                    + "Hasil Pemeriksaan! Hanya data valid sebelumnya yang berhasil diubah");
                }
                komponenPemeriksaanRestService.ubahKomponenPemeriksaan(komponenPemeriksaanData.getId(),
                        komponenPemeriksaanTemp);
            }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Komponen Pemeriksaan tidak ditemukan!");
        }

        hasilPemeriksaan = hasilPemeriksaanRestService.ubahStatus(hasilPemeriksaan.getIdHasilPemeriksaan(),
                hasilPemeriksaanDTO.getIdStatus());
        for (KomponenPemeriksaanDTO komponenPemeriksaanData : hasilPemeriksaanDTO.getDaftarKomponenPemeriksaan()) {
            KomponenPemeriksaan komponenPemeriksaan = komponenPemeriksaanRestService
                    .getById(komponenPemeriksaanData.getId());

            List<TemuanRisiko> daftarTemuanRisikoTersimpan = temuanRisikoRestService
                    .getByKomponenPemeriksaan(komponenPemeriksaan);
            if (!daftarTemuanRisikoTersimpan.isEmpty()) {
                for (TemuanRisiko temuanRisikoTersimpan : daftarTemuanRisikoTersimpan) {
                    boolean hapusTemuanRisiko = true;
                    if (komponenPemeriksaanData.getDaftarTemuanRisikoTerdaftar() != null
                            && !komponenPemeriksaanData.getDaftarTemuanRisikoTerdaftar().isEmpty()) {
                        for (TemuanRisikoDTO temuanRisikoTerdaftar : komponenPemeriksaanData
                                .getDaftarTemuanRisikoTerdaftar()) {
                            if (temuanRisikoTerdaftar.getKeterangan() != null
                                    && !temuanRisikoTerdaftar.getKeterangan().equals("")
                                    && temuanRisikoTersimpan == temuanRisikoRestService
                                    .getById(temuanRisikoTerdaftar.getId())) {
                                hapusTemuanRisiko = false;
                                temuanRisikoTersimpan.setKeterangan(temuanRisikoTerdaftar.getKeterangan());
                                temuanRisikoRestService.ubahTemuanRisiko(temuanRisikoTersimpan.getIdTemuanRisiko(),
                                        temuanRisikoTersimpan);
                            }
                        }
                        if (hapusTemuanRisiko)
                            temuanRisikoRestService.hapusTemuanRisiko(temuanRisikoTersimpan.getIdTemuanRisiko());
                    } else {
                        temuanRisikoRestService.hapusTemuanRisiko(temuanRisikoTersimpan.getIdTemuanRisiko());
                    }
                }
            }

            List<Rekomendasi> daftarRekomendasiTersimpan = rekomendasiRestService
                    .getByKomponenPemeriksaan(komponenPemeriksaan);
            if (!daftarRekomendasiTersimpan.isEmpty()) {
                for (Rekomendasi rekomendasiTersimpan : daftarRekomendasiTersimpan) {
                    boolean hapusRekomendasi = true;
                    if (komponenPemeriksaanData.getDaftarRekomendasiTerdaftar() != null
                            && !komponenPemeriksaanData.getDaftarRekomendasiTerdaftar().isEmpty()) {
                        for (RekomendasiDTO rekomendasiTerdaftar : komponenPemeriksaanData
                                .getDaftarRekomendasiTerdaftar()) {
                            if (rekomendasiTerdaftar.getKeterangan() != null
                                    && !rekomendasiTerdaftar.getKeterangan().equals("")
                                    && rekomendasiTersimpan == rekomendasiRestService
                                    .getById(rekomendasiTerdaftar.getId())) {
                                hapusRekomendasi = false;
                                rekomendasiTersimpan.setKeterangan(rekomendasiTerdaftar.getKeterangan());
                                rekomendasiTersimpan.setStatusRekomendasi(
                                        statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getIdStatus()));
                                rekomendasiRestService.ubahRekomendasi(rekomendasiTersimpan.getIdRekomendasi(),
                                        rekomendasiTersimpan);
                            }
                        }
                        if (hapusRekomendasi)
                            rekomendasiRestService.hapusRekomendasi(rekomendasiTersimpan.getIdRekomendasi());
                    } else {
                        rekomendasiRestService.hapusRekomendasi(rekomendasiTersimpan.getIdRekomendasi());
                    }
                }
            }

            createTemuanRisikoAndRekomendasi(hasilPemeriksaanDTO, employee, komponenPemeriksaanData,
                    komponenPemeriksaan);

        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(hasilPemeriksaan);

        return response;
    }

    private void createTemuanRisikoAndRekomendasi(@RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
                                                  Employee employee, KomponenPemeriksaanDTO komponenPemeriksaanData,
                                                  KomponenPemeriksaan komponenPemeriksaan) {
        if (komponenPemeriksaanData.getDaftarTemuanRisiko() != null
                && !komponenPemeriksaanData.getDaftarTemuanRisiko().isEmpty())
            for (TemuanRisikoDTO temuanRisikoData : komponenPemeriksaanData.getDaftarTemuanRisiko()) {
                if (temuanRisikoData.getKeterangan() != null && !temuanRisikoData.getKeterangan().equals("")) {
                    TemuanRisiko temuanRisiko = new TemuanRisiko();
                    temuanRisiko.setKomponenPemeriksaan(komponenPemeriksaan);
                    temuanRisiko.setKeterangan(temuanRisikoData.getKeterangan());
                    temuanRisiko.setPembuat(employee);
                    temuanRisikoRestService.buatTemuanRisiko(temuanRisiko);
                }
            }

        if (komponenPemeriksaanData.getDaftarRekomendasi() != null
                && !komponenPemeriksaanData.getDaftarRekomendasi().isEmpty())
            for (RekomendasiDTO rekomendasiData : komponenPemeriksaanData.getDaftarRekomendasi()) {
                if (rekomendasiData.getKeterangan() != null && !rekomendasiData.getKeterangan().equals("")) {
                    Rekomendasi rekomendasi = new Rekomendasi();
                    rekomendasi.setKomponenPemeriksaan(komponenPemeriksaan);
                    rekomendasi.setKeterangan(rekomendasiData.getKeterangan());
                    rekomendasi.setStatusRekomendasi(
                            statusRekomendasiRestService.getById(hasilPemeriksaanDTO.getIdStatus()));
                    rekomendasi.setPembuat(employee);
                    rekomendasiRestService.buatRekomendasi(rekomendasi);
                }
            }
    }

    /**
     * Menghapus hasil pemeriksaan
     *
     * @param hasilPemeriksaanDTO data transfer object untuk hasil pemeriksaan yang
     *                            akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusHasilPemeriksaan(@RequestBody HasilPemeriksaanDTO hasilPemeriksaanDTO,
                                                       Principal principal) {
        BaseResponse<String> response = new BaseResponse<>();
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        HasilPemeriksaan hasilPemeriksaan;
        try {
            hasilPemeriksaan = hasilPemeriksaanRestService.getById(hasilPemeriksaanDTO.getId());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hasil Pemeriksaan tidak ditemukan!");
        }

        if (hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil() != 1
                && hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil() != 2
                && hasilPemeriksaan.getStatusHasilPemeriksaan().getIdStatusHasil() != 3)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Hasil Pemeriksaan tidak boleh dihapus!");

        if (employee != hasilPemeriksaan.getTugasPemeriksaan().getPelaksana()
                && employee.getRole() == roleRestService.getById(5))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee()
                    + " tidak ditugaskan untuk menghapus hasil pemeriksaan ini!");

        hasilPemeriksaanRestService.hapusHasilPemeriksaan(hasilPemeriksaan.getIdHasilPemeriksaan());

        response.setStatus(200);
        response.setMessage("success");
        response.setResult("Hasil pemeriksaan dengan id " + hasilPemeriksaanDTO.getId() + " terhapus!");
        return response;
    }

    /**
     * Menyetujui atai menolak hasil pemeriksaan
     *
     * @param persetujuanHasilPemeriksaanDTO data transfer object untuk persetujuan
     *                                       hasil pemeriksaan
     */
    @PostMapping(value = "/persetujuan", consumes = {"application/json"})
    private BaseResponse<String> persetujuanHasilPemeriksaan(
            @RequestBody PersetujuanHasilPemeriksaanDTO persetujuanHasilPemeriksaanDTO, Principal principal,
            ModelMap model) {
        BaseResponse<String> response = new BaseResponse<>();

        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        HasilPemeriksaan hasilPemeriksaanTemp = hasilPemeriksaanRestService
                .validateExistById(persetujuanHasilPemeriksaanDTO.getId());

        try {
            StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanRestService
                    .getById(persetujuanHasilPemeriksaanDTO.getStatus());
            if (hasilPemeriksaanTemp.getStatusHasilPemeriksaan().getIdStatusHasil() != 2
                    || (statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(3)
                    && statusHasilPemeriksaan != statusHasilPemeriksaanRestService.getById(4)))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pengajuan persetujuan tidak diperbolehkan!");
            hasilPemeriksaanTemp.setStatusHasilPemeriksaan(statusHasilPemeriksaan);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status Hasil Pemeriksaan tidak ditemukan!");
        }

        if (persetujuanHasilPemeriksaanDTO.getStatus() == 3 && (persetujuanHasilPemeriksaanDTO.getFeedback() == null
                || persetujuanHasilPemeriksaanDTO.getFeedback().equals("")))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Feedback perlu diisi untuk penolakan Hasil Pemeriksaan!");

        hasilPemeriksaanTemp.setPemeriksa(employee);
        hasilPemeriksaanTemp.setFeedback(persetujuanHasilPemeriksaanDTO.getFeedback());
        HasilPemeriksaan hasilPemeriksaan = hasilPemeriksaanRestService
                .buatHasilPemeriksaan(persetujuanHasilPemeriksaanDTO.getId(), hasilPemeriksaanTemp);

        for (KomponenPemeriksaan komponenPemeriksaan : komponenPemeriksaanRestService
                .getByHasilPemeriksaan(hasilPemeriksaan)) {
            for (Rekomendasi rekomendasi : rekomendasiRestService.getByKomponenPemeriksaan(komponenPemeriksaan)) {
                rekomendasi.setStatusRekomendasi(
                        statusRekomendasiRestService.getById(persetujuanHasilPemeriksaanDTO.getStatus()));
                rekomendasiRestService.ubahRekomendasi(rekomendasi.getIdRekomendasi(), rekomendasi);
            }
        }

        if (persetujuanHasilPemeriksaanDTO.getStatus() == 3) {
            response.setResult("Hasil Pemeriksaan dengan id " + persetujuanHasilPemeriksaanDTO.getId() + " ditolak!");
        } else {
            response.setResult("Hasil Pemeriksaan dengan id " + persetujuanHasilPemeriksaanDTO.getId() + " disetujui!");
        }
        response.setStatus(200);
        response.setMessage("success");

        return response;
    }

    @GetMapping(value = "/jalankan/{id}")
    private BaseResponse<String> jalankanHasilPemeriksaan(@PathVariable("id") int id, Principal principal) {
        employeeRestService.validateEmployeeExistByPrincipal(principal);

        HasilPemeriksaan target = hasilPemeriksaanRestService.getById(id);

        List<KomponenPemeriksaan> komponenPemeriksaanList = komponenPemeriksaanRestService
                .getByHasilPemeriksaan(target);

        List<Rekomendasi> rekomendasiList = rekomendasiRestService
                .getByDaftarKomponenPemeriksaan(komponenPemeriksaanList);

        for (Rekomendasi rekomendasi : rekomendasiList) {
            rekomendasiRestService.jalankan(rekomendasi);
        }

        hasilPemeriksaanRestService.ubahStatus(target.getIdHasilPemeriksaan(), 5);

        return new BaseResponse<>(200, "sukses", "sukses");
    }
}