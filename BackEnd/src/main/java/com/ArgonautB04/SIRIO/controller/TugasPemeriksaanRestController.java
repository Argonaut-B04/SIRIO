package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.TugasPemeriksaanRestService;
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
@RequestMapping("/api/v1/TugasPemeriksaan")
public class TugasPemeriksaanRestController {

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan pelaksana tugas tersebut
     *
     * @param idPelaksana identifier pelaksanan
     *
     * @return daftar tugas pemeriksaan yang terhubung dengan pelaksana tersebut
     */
    @GetMapping("/getAll/{idPelaksana}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukPelaksana(
            @PathVariable("idPelaksana") int idPelaksana
    ) {
        BaseResponse<List<TugasPemeriksaan>> response = new BaseResponse<>();
        try {
            Employee pelaksana = employeeRestService.getById(idPelaksana);
            List<TugasPemeriksaan> result = tugasPemeriksaanRestService.getByPelaksana(pelaksana);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + idPelaksana + " tidak ditemukan!"
            );
        }
        return response;
    }

}