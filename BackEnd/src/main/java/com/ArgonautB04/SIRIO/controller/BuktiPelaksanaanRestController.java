package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.BuktiPelaksanaanDTO;
import com.ArgonautB04.SIRIO.rest.PersetujuanBuktiPelaksanaanDTO;
import com.ArgonautB04.SIRIO.services.BuktiPelaksanaanRestService;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.StatusBuktiPelaksanaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

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

    /**
     * Mengambil seluruh bukti pelaksanaan
     *
     * @return daftar bukti pelaksanaan
     */
    @GetMapping("/getAll")
    private BaseResponse<List<BuktiPelaksanaan>> getAllBuktiPelaksanaan() {
        BaseResponse<List<BuktiPelaksanaan>> response = new BaseResponse<>();
        List<BuktiPelaksanaan> result = buktiPelaksanaanRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
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
        BaseResponse<List<BuktiPelaksanaan>> response = new BaseResponse<>();
        try {
            Employee pembuat = employeeRestService.getById(idPembuat);
            List<BuktiPelaksanaan> result = buktiPelaksanaanRestService.getByPembuat(pembuat);

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
     * Mengambil suatu bukti pelaksanaan
     *
     * @param idBuktiPelaksanaan identifier bukti pelaksanaan
     * @return detail bukti pelaksanaan
     */
    @GetMapping("/{idBuktiPelaksanaan}")
    private BaseResponse<BuktiPelaksanaan> getBuktiPelaksanaan(
            @PathVariable("idBuktiPelaksanaan") int idBuktiPelaksanaan
    ) {
        BaseResponse<BuktiPelaksanaan> response = new BaseResponse<>();
        try {
            BuktiPelaksanaan result = buktiPelaksanaanRestService.getById(idBuktiPelaksanaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Bukti Pelaksanaan dengan ID " + idBuktiPelaksanaan + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menambah bukti pelaksanaan baru untuk rekomendasi spesifik
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan yang akan ditambah
     * @return bukti pelaksanaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<BuktiPelaksanaan> tambahBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO
    ) {
        BaseResponse<BuktiPelaksanaan> response = new BaseResponse<>();
        BuktiPelaksanaan buktiPelaksanaanTemp = new BuktiPelaksanaan();

        buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(
                statusBuktiPelaksanaanRestService.getById(1));
        buktiPelaksanaanTemp.setKeterangan(buktiPelaksanaanDTO.getKeterangan());
        buktiPelaksanaanTemp.setLampiran(buktiPelaksanaanDTO.getLampiran());

        try {
            Employee pembuat = employeeRestService.getById(buktiPelaksanaanDTO.getIdPembuat());
            buktiPelaksanaanTemp.setPembuat(pembuat);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + buktiPelaksanaanDTO.getIdPembuat() + " tidak ditemukan!"
            );
        }

        try {
            Rekomendasi rekomendasi = rekomendasiRestService.getById(buktiPelaksanaanDTO.getIdRekomendasi());
            buktiPelaksanaanTemp.setRekomendasi(rekomendasi);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + buktiPelaksanaanDTO.getIdRekomendasi() + " tidak ditemukan!"
            );
        }

        BuktiPelaksanaan buktiPelaksanaan = buktiPelaksanaanRestService.buatBuktiPelaksanaan(buktiPelaksanaanTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(buktiPelaksanaan);

        return response;
    }

    /**
     * Mengubah bukti pelaksanaan
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan yang akan diubah
     * @return bukti pelaksanaan yang telah disimpan
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<BuktiPelaksanaan> ubahBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO
    ) {
        BaseResponse<BuktiPelaksanaan> response = new BaseResponse<>();
        try {
            BuktiPelaksanaan buktiPelaksanaanTemp = buktiPelaksanaanRestService.getById(buktiPelaksanaanDTO.getId());
            buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(
                    statusBuktiPelaksanaanRestService.getById(1));
            buktiPelaksanaanTemp.setKeterangan(buktiPelaksanaanDTO.getKeterangan());
            buktiPelaksanaanTemp.setLampiran(buktiPelaksanaanDTO.getLampiran());
            BuktiPelaksanaan buktiPelaksanaan =
                    buktiPelaksanaanRestService.ubahBuktiPelaksanaan(buktiPelaksanaanDTO.getId(), buktiPelaksanaanTemp);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(buktiPelaksanaan);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Bukti Pelaksanaan dengan ID " + buktiPelaksanaanDTO.getId() + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menyetujui atau menolak bukti pelaksanaan
     *
     * @param persetujuanBuktiPelaksanaanDTO data transfer object untuk persetujuan bukti pelaksanaan
     */
    @PutMapping(value = "/persetujuan", consumes = {"application/json"})
    private BaseResponse<String> persetujuanBuktiPelaksanaan(
            @RequestBody PersetujuanBuktiPelaksanaanDTO persetujuanBuktiPelaksanaanDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            BuktiPelaksanaan buktiPelaksanaanTemp = buktiPelaksanaanRestService.getById(
                    persetujuanBuktiPelaksanaanDTO.getIdBuktiPelaksanaan());
            buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(statusBuktiPelaksanaanRestService.getById(
                    persetujuanBuktiPelaksanaanDTO.getStatus()));
            buktiPelaksanaanTemp.setFeedback(persetujuanBuktiPelaksanaanDTO.getFeedback());

            try {
                Employee pemeriksa = employeeRestService.getById(persetujuanBuktiPelaksanaanDTO.getIdPemeriksa());
                buktiPelaksanaanTemp.setPemeriksa(pemeriksa);
            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee dengan ID " + persetujuanBuktiPelaksanaanDTO.getIdPemeriksa() + " tidak ditemukan!"
                );
            }

            try {
                Rekomendasi rekomendasi = rekomendasiRestService.getById(persetujuanBuktiPelaksanaanDTO.getIdRekomendasi());
                buktiPelaksanaanTemp.setRekomendasi(rekomendasi);
            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + persetujuanBuktiPelaksanaanDTO.getIdRekomendasi() + " tidak ditemukan!"
                );
            }

            buktiPelaksanaanRestService.ubahBuktiPelaksanaan(
                    persetujuanBuktiPelaksanaanDTO.getIdBuktiPelaksanaan(), buktiPelaksanaanTemp);

            if (persetujuanBuktiPelaksanaanDTO.getStatus() == 3) {
                response.setResult("Bukti pelaksanaan dengan id " +
                        persetujuanBuktiPelaksanaanDTO.getIdBuktiPelaksanaan() + " ditolak!");
            } else {
                response.setResult("Bukti pelaksanaan dengan id " +
                        persetujuanBuktiPelaksanaanDTO.getIdBuktiPelaksanaan() + " disetujui!");
            }
            response.setStatus(200);
            response.setMessage("success");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Bukti pelaksanaan dengan id " +
                    persetujuanBuktiPelaksanaanDTO.getIdBuktiPelaksanaan() + " tidak dapat ditemukan");
        }
        return response;
    }
}
