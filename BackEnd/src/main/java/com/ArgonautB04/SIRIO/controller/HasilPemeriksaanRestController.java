package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.HasilPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/HasilPemeriksaan")
public class HasilPemeriksaanRestController {

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Mengambil seluruh hasil pemeriksaan
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
}
