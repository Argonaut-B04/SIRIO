package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/KantorCabang")
public class KantorCabangRestController {

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private RiskRatingRestService riskRatingRestService;

    /**
     * Mengambil seluruh kantor cabang
     *
     * @return daftar kantor cabang
     */
    @GetMapping("/getAll")
    private BaseResponse<List<KantorCabang>> getAllKantorCabang() {
        BaseResponse<List<KantorCabang>> response = new BaseResponse<>();
        List<KantorCabang> result = kantorCabangRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil suatu kantor cabang
     *
     * @param idKantorCabang identifier kantor cabang
     * @return detail kantor cabang
     */
    @GetMapping("/{idKantorCabang}")
    private BaseResponse<KantorCabang> getKantorCabang(
            @PathVariable("idKantorCabang") int idKantorCabang
    ) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        try {
            KantorCabang result = kantorCabangRestService.getById(idKantorCabang);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kantor cabang dengan ID " + idKantorCabang + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menambah kantor cabang baru
     *
     * @param kantorCabangDTO data transfer object untuk kantor cabang yang akan ditambah
     * @return kantor cabang yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<KantorCabang> tambahKantorCabang(
            @RequestBody KantorCabangDTO kantorCabangDTO
    ) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        KantorCabang kantorCabangTemp = new KantorCabang();

        try {
            Employee pembuat = employeeRestService.getById(kantorCabangDTO.getIdPembuat());
            kantorCabangTemp.setPembuat(pembuat);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + kantorCabangDTO.getIdPembuat() + " tidak ditemukan!"
            );
        }

        try {
            Employee pemilik = employeeRestService.getById(kantorCabangDTO.getIdPemilik());
            kantorCabangTemp.setPemilik(pemilik);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + kantorCabangDTO.getIdPemilik() + " tidak ditemukan!"
            );
        }

        kantorCabangTemp.setArea(kantorCabangDTO.getArea());
        kantorCabangTemp.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        kantorCabangTemp.setRegional(kantorCabangDTO.getRegional());

        KantorCabang kantorCabang = kantorCabangRestService.buatKantorCabang(kantorCabangTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(kantorCabang);

        return response;

    }
}
