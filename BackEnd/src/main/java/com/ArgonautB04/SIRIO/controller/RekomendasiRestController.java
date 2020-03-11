package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.Settings;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/Rekomendasi")
public class RekomendasiRestController {

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Mengambil seluruh rekomendasi yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar rekomendasi yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<Rekomendasi>> getAllRekomendasiUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        BaseResponse<List<Rekomendasi>> response = new BaseResponse<>();
        try {
            Employee pembuat = employeeRestService.getById(idPembuat);
            List<Rekomendasi> result = rekomendasiRestService.getByPembuat(pembuat);

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
     * Mengubah tenggat waktu untuk rekomendasi spesifik. (UNTESTED)
     *
     * @param idRekomendasi identifier rekomendasi yang akan diubah tenggat waktunya
     * @param tenggatWaktu  dalam format yyyy-MM-dd (format default html)
     * @return objek rekomendasi yang telah diubah
     */
    @PutMapping("/ubah/{idRekomendasi}")
    private BaseResponse<Rekomendasi> ubahTenggatWaktu(
            @PathVariable("idRekomendasi") int idRekomendasi,
            @RequestBody String tenggatWaktu
    ) {
        BaseResponse<Rekomendasi> response = new BaseResponse<>();
        try {
            LocalDate tenggatWaktuLocalDate = Settings.stringToLocalDate(tenggatWaktu);
            Rekomendasi result = rekomendasiRestService.ubahTenggatWaktu(idRekomendasi, tenggatWaktuLocalDate);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        }
        return response;
    }
}
