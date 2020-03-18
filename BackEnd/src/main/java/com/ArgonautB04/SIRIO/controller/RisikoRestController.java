package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.HasilPemeriksaanDTO;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;
import com.ArgonautB04.SIRIO.services.RisikoRestService;
import com.ArgonautB04.SIRIO.services.SOPRestService;
import com.ArgonautB04.SIRIO.services.StatusRisikoRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/Risiko")
public class RisikoRestController {

    @Autowired
    private RisikoRestService risikoRestService;

    @Autowired
    private StatusRisikoRestService statusRisikoRestService;

    @Autowired
    private SOPRestService sopRestService;

    /**
     * Menambahkan risiko baru.
     * @param risikoDTO data transfer object untuk risiko yang akan ditambah
     * @return menyimpan risiko baru ke db
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<Risiko> tambahRisiko(
            @RequestBody RisikoDTO risikoDTO
    ) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        Risiko risikoTemp = new Risiko();
        List<Risiko> childTemp = new ArrayList<>();
        risikoTemp.setChildList(childTemp);
        risikoTemp.setStatusRisiko(
                statusRisikoRestService.getById(risikoDTO.getStatus()));
        risikoTemp.setSop(sopRestService.getById(risikoDTO.getSop()));
        risikoTemp.setKomponen(risikoDTO.getKomponen());
        risikoTemp.setRisikoKategori(risikoDTO.getKategori());

//        try {
//            SOP sop = sopRestService.getById(sopDTO.getId());
//            risikoTemp.setSop(sop);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "SOP dengan ID " + sopDTO.getId() + " tidak ditemukan!"
//            );
//        }

        Risiko risiko = risikoRestService.buatRisiko(risikoTemp);
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(risiko);

        return response;
    }

    /**
     * Menampilkan satu risiko.
     * @param idRisiko
     * @return object risiko beserta atribut yang sesuai dengan idRisiko tersebut
     */
    @GetMapping(value = "/{idRisiko}")
    private BaseResponse<Risiko> getRisiko(@PathVariable("idRisiko") int idRisiko) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        try {
            Risiko result = risikoRestService.getById(idRisiko);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ID Risiko " + String.valueOf(idRisiko) + " Tidak Ditemukan");
        }
        return response;
    }

    /**
     * Menghapus satu risiko.
     * @param risikoDTO data transfer object untuk risiko yang akan dihapus
     * @return risiko dengan idRisiko tersebut dihapus dari db
     */
    @DeleteMapping(value = "/{idRisiko}")
    private BaseResponse<String> hapusRisiko(@RequestBody RisikoDTO risikoDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            risikoRestService.hapusRisiko(risikoDTO.getId());

            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Risiko dengan id " + risikoDTO.getId() + " terhapus!");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Risiko dengan id " + risikoDTO.getId() + " tidak dapat ditemukan");
        }
        return response;
    }

    /**
     * Mengubah risiko, semua atribut dapat diubah.
     * @param risikoDTO data transfer object untuk risiko yang akan diubah
     * @return perubahan data akan disimpan di db
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<Risiko> ubahRisiko(
            @RequestBody RisikoDTO risikoDTO
    ) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        Risiko risikoTemp = risikoRestService.getById(risikoDTO.getId());
        risikoTemp.setStatusRisiko(
                statusRisikoRestService.getById(risikoDTO.getStatus()));
        Risiko result = risikoRestService.ubahRisiko(risikoTemp.getIdRisiko(), risikoTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);

        return response;

    }

    /**
     * Menampilkan daftar risiko-risiko yang sudah ada.
     * @return daftar risiko berupa list.
     */
    @GetMapping("/getAll")
    private BaseResponse<List<Risiko>> getAllRisiko() {
        BaseResponse<List<Risiko>> response = new BaseResponse<>();
        List<Risiko> result = risikoRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }
}
