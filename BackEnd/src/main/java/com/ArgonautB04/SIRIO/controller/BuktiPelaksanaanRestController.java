package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.BuktiPelaksanaanDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/BuktiPelaksanaan")
public class BuktiPelaksanaanRestController {

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private StatusBuktiPelaksanaanRestService statusBuktiPelaksanaanRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private StatusRekomendasiRestService statusRekomendasiRestService;

    /**
     * Mengambil seluruh bukti pelaksanaan
     *
     * @return daftar bukti pelaksanaan
     */
    @GetMapping("/getAll")
    private BaseResponse<List<BuktiPelaksanaan>> getAllBuktiPelaksanaan() {
        return new BaseResponse<>(200, "success", buktiPelaksanaanRestService.getAll());
    }

    /**
     * Mengambil seluruh bukti pelaksanaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar bukti pelaksanaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<BuktiPelaksanaan>> getAllBuktiPelaksanaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        Employee pembuat = employeeRestService.validateEmployeeExistById(idPembuat);
        List<BuktiPelaksanaan> result = buktiPelaksanaanRestService.getByPembuat(pembuat);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil suatu bukti pelaksanaan
     *
     * @param idBuktiPelaksanaan identifier bukti pelaksanaan
     * @return detail bukti pelaksanaan
     */
    @GetMapping("/{idBuktiPelaksanaan}")
    private BaseResponse<BuktiPelaksanaanDTO> getDetailBuktiPelaksanaan(
            @PathVariable("idBuktiPelaksanaan") int idBuktiPelaksanaan, Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "bukti pelaksanaan");

        BuktiPelaksanaan buktiPelaksanaan = buktiPelaksanaanRestService.validateExistById(idBuktiPelaksanaan);

        BuktiPelaksanaanDTO result = new BuktiPelaksanaanDTO();
        result.setId(buktiPelaksanaan.getIdBuktiPelaksanaan());
        result.setStatus(buktiPelaksanaan.getStatusBuktiPelaksanaan().getIdStatusBukti());
        result.setKeterangan(buktiPelaksanaan.getKeterangan());
        result.setLampiran(buktiPelaksanaan.getLampiran());
        result.setFeedback(buktiPelaksanaan.getFeedback());
        result.setNamaPembuat(buktiPelaksanaan.getPembuat().getNama());
        result.setIdRekomendasi(buktiPelaksanaan.getRekomendasi().getIdRekomendasi());
        result.setKeteranganRekomendasi(buktiPelaksanaan.getRekomendasi().getKeterangan());
        result.setStatusRekomendasi(buktiPelaksanaan.getRekomendasi().getStatusRekomendasi().getIdStatusRekomendasi());

        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Menambah bukti pelaksanaan baru untuk rekomendasi spesifik
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan yang akan ditambah
     * @return bukti pelaksanaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<BuktiPelaksanaan> tambahBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "tambah bukti pelaksanaan");

        BuktiPelaksanaan buktiPelaksanaanTemp = new BuktiPelaksanaan();
        buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(statusBuktiPelaksanaanRestService.getById(1));

        if (buktiPelaksanaanDTO.getKeterangan() != null && !buktiPelaksanaanDTO.getKeterangan().equals("")) {
            buktiPelaksanaanTemp.setKeterangan(buktiPelaksanaanDTO.getKeterangan());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Keterangan bukti pelaksanaan perlu diisi!"
            );
        }
        if (buktiPelaksanaanDTO.getLampiran() != null && !buktiPelaksanaanDTO.getLampiran().equals("")) {
            if (buktiPelaksanaanDTO.getLampiran().contains("https://") |
                    buktiPelaksanaanDTO.getLampiran().contains("http://") |
                    buktiPelaksanaanDTO.getLampiran().contains("www")) {
                buktiPelaksanaanTemp.setLampiran(buktiPelaksanaanDTO.getLampiran());
            } else {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Lampiran bukti pelaksanaan harus berupa link url!"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Lampiran bukti pelaksanaan perlu diisi!"
            );
        }

        buktiPelaksanaanTemp.setPembuat(pengelola);

        Rekomendasi rekomendasi = rekomendasiRestService.validateExistInById(buktiPelaksanaanDTO.getIdRekomendasi());
        buktiPelaksanaanTemp.setRekomendasi(rekomendasi);
        rekomendasi.setBuktiPelaksanaan(buktiPelaksanaanTemp);
        rekomendasiRestService.buatRekomendasi(rekomendasi);

        return new BaseResponse<>(200, "success", buktiPelaksanaanTemp);
    }

    /**
     * Mengubah bukti pelaksanaan
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan yang akan diubah
     * @return bukti pelaksanaan yang telah disimpan
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<BuktiPelaksanaan> ubahBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO, Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "ubah bukti pelaksanaan");

        BuktiPelaksanaan buktiPelaksanaanTemp = buktiPelaksanaanRestService.validateExistById(buktiPelaksanaanDTO.getId());
        buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(
                statusBuktiPelaksanaanRestService.getById(1));

        if (buktiPelaksanaanDTO.getKeterangan() != null && !buktiPelaksanaanDTO.getKeterangan().equals("")) {
            buktiPelaksanaanTemp.setKeterangan(buktiPelaksanaanDTO.getKeterangan());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Keterangan bukti pelaksanaan perlu diisi!"
            );
        }

        if (buktiPelaksanaanDTO.getLampiran() != null && !buktiPelaksanaanDTO.getLampiran().equals("")) {
            if (buktiPelaksanaanDTO.getLampiran().contains("https://") |
                    buktiPelaksanaanDTO.getLampiran().contains("http://") |
                    buktiPelaksanaanDTO.getLampiran().contains("www")) {
                buktiPelaksanaanTemp.setLampiran(buktiPelaksanaanDTO.getLampiran());
            } else {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Lampiran bukti pelaksanaan harus berupa link url!"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Lampiran bukti pelaksanaan perlu diisi!"
            );
        }

        BuktiPelaksanaan buktiPelaksanaan =
                buktiPelaksanaanRestService.ubahBuktiPelaksanaan(buktiPelaksanaanDTO.getId(), buktiPelaksanaanTemp);

        return new BaseResponse<>(200, "success", buktiPelaksanaan);
    }

    /**
     * Menyetujui atau menolak bukti pelaksanaan
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan
     */
    @PostMapping(value = "/persetujuan", consumes = {"application/json"})
    private BaseResponse<String> persetujuanBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "persetujuan bukti pelaksanaan");
        BuktiPelaksanaan buktiPelaksanaanTemp = buktiPelaksanaanRestService.validateExistById(buktiPelaksanaanDTO.getId());
        buktiPelaksanaanTemp.setPemeriksa(pengelola);
        Rekomendasi rekomendasi = rekomendasiRestService.getById(buktiPelaksanaanTemp.getRekomendasi().getIdRekomendasi());

        try {
            StatusBuktiPelaksanaan statusBuktiPelaksanaan = statusBuktiPelaksanaanRestService.getById(
                    buktiPelaksanaanDTO.getStatus());
            if (buktiPelaksanaanTemp.getStatusBuktiPelaksanaan().getIdStatusBukti() != 1 ||
                    (statusBuktiPelaksanaan != statusBuktiPelaksanaanRestService.getById(2) &&
                            statusBuktiPelaksanaan != statusBuktiPelaksanaanRestService.getById(3)))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Pengajuan persetujuan tidak diperbolehkan!"
                );
            buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(statusBuktiPelaksanaan);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Status bukti pelaksanaan tidak ditemukan!"
            );
        }
        StatusRekomendasi statusRekomendasi = statusRekomendasiRestService.getById(
                buktiPelaksanaanDTO.getStatusRekomendasi());
        rekomendasi.setStatusRekomendasi(statusRekomendasi);

        if (buktiPelaksanaanDTO.getStatus() == 3 && (buktiPelaksanaanDTO.getFeedback() == null ||
                buktiPelaksanaanDTO.getFeedback().equals("")))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Feedback perlu diisi untuk penolakan bukti pelaksanaan!"
            );
        buktiPelaksanaanTemp.setFeedback(buktiPelaksanaanDTO.getFeedback());

        buktiPelaksanaanTemp.setTanggalPersetujuan(LocalDate.now());

        buktiPelaksanaanRestService.ubahBuktiPelaksanaan(buktiPelaksanaanDTO.getId(), buktiPelaksanaanTemp);

        String result;
        if (buktiPelaksanaanDTO.getStatus() == 3) {
            result = "Bukti pelaksanaan dengan id " + buktiPelaksanaanDTO.getId() + " ditolak!";
        } else {
            result = "Bukti pelaksanaan dengan id " + buktiPelaksanaanDTO.getId() + " disetujui!";
        }
        return new BaseResponse<>(200, "success", result);
    }
}
