package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;

import com.ArgonautB04.SIRIO.services.RencanaPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/RencanaPemeriksaan")
public class RencanaPemeriksaanRestController {

    @Autowired
    private RencanaPemeriksaanRestService rencanaPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private StatusRencanaPemeriksaanRestService statusRencanaPemeriksaanRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    /**
     * Mengambil seluruh rencana pemeriksaan
     *
     * @return daftar rencana pemeriksaan
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RencanaPemeriksaan>> getAllRencanaPemeriksaan() {
        BaseResponse<List<RencanaPemeriksaan>> response = new BaseResponse<>();
        List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh rencana pemeriksaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar rencana pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<RencanaPemeriksaan>> getAllRencanaPemeriksaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        BaseResponse<List<RencanaPemeriksaan>> response = new BaseResponse<>();
        try {
            Employee pembuat = employeeRestService.getById(idPembuat);
            List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getByPembuat(pembuat);

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
     * Mengambil suatu rencana pemeriksaan
     *
     * @param idRencanaPemeriksaan identifier rencana pemeriksaan
     * @return detail rencana pemeriksaan
     */
    @GetMapping("/{idRencanaPemeriksaan}")
    private BaseResponse<RencanaPemeriksaan> getRencanaPemeriksaan(
            @PathVariable("idRencanaPemeriksaan") int idRencanaPemeriksaan
    ) {
        BaseResponse<RencanaPemeriksaan> response = new BaseResponse<>();
        try {
            RencanaPemeriksaan result = rencanaPemeriksaanRestService.getById(idRencanaPemeriksaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rencana Pemeriksaan dengan ID " + idRencanaPemeriksaan + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menambah rencana pemeriksaan baru
     *
     * @param rencanaPemeriksaanDTO data transfer object untuk rencana pemeriksaan yang akan ditambah
     * @return rencana pemeriksaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<RencanaPemeriksaan> tambahRencanaPemeriksaan(
            @RequestBody RencanaPemeriksaanDTO rencanaPemeriksaanDTO
    ) {
        BaseResponse<RencanaPemeriksaan> response = new BaseResponse<>();
        RencanaPemeriksaan rencanaPemeriksaanTemp = new RencanaPemeriksaan();

        rencanaPemeriksaanTemp.setNamaRencana(rencanaPemeriksaanDTO.getNamaRencana());
        rencanaPemeriksaanTemp.setLinkMajelis(rencanaPemeriksaanDTO.getLinkMajelis());
        rencanaPemeriksaanTemp.setPembuat(employeeRestService.getById(rencanaPemeriksaanDTO.getIdPembuat()));
        rencanaPemeriksaanTemp.setStatus(statusRencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getStatus()));

        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.buatRencanaPemeriksaan(rencanaPemeriksaanTemp);

        for (TugasPemeriksaanDTO tugasPemeriksaanDTO: rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()){
            TugasPemeriksaan tugasPemeriksaanTemp = new TugasPemeriksaan();
            tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
            tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));

            LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
            tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);

            LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());
            tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);

            TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.buatTugasPemeriksaan(tugasPemeriksaanTemp);
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(rencanaPemeriksaan);

        return response;
    }

    /**
     * Mengubah rencana pemeriksaan
     *
     * @param rencanaPemeriksaanDTO data transfer object untuk rencana pemeriksaan yang akan diubah
     * @return rencana pemeriksaan yang telah disimpan perubahannya
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<RencanaPemeriksaan> ubahRencanaPemeriksaan(
            @RequestBody RencanaPemeriksaanDTO rencanaPemeriksaanDTO
    ) {
        BaseResponse<RencanaPemeriksaan> response = new BaseResponse<>();
        RencanaPemeriksaan rencanaPemeriksaanTemp = rencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getId());
        rencanaPemeriksaanTemp.setStatus(statusRencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getStatus()));
        for (TugasPemeriksaanDTO tugasPemeriksaanDTO: rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()){
            TugasPemeriksaan tugasPemeriksaanTemp = tugasPemeriksaanRestService.getById(tugasPemeriksaanDTO.getId());
            TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
        }

        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.ubahRencanaPemeriksaan(rencanaPemeriksaanDTO.getId(), rencanaPemeriksaanTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(rencanaPemeriksaan);

        return response;
    }

    /**
     * Menghapus rencana pemeriksaan
     *
     * @param rencanaPemeriksaanDTO data transfer object untuk rencana pemeriksaan yang akan dihapus
     */
    @DeleteMapping("/hapus")
    private BaseResponse<String> hapusRencanaPemeriksaan(
            @RequestBody RencanaPemeriksaanDTO rencanaPemeriksaanDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getId());

        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Rencana pemeriksaan id " + rencanaPemeriksaanDTO.getId() + " tidak dapat ditemukan");
        }

       /* Tidak dapat dihapus jika rencana sudah dijalankan
        if(rencanaPemeriksaanDTO.getStatus() == 2){
            response.setStatus(500);
            response.setMessage("error");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " tidak dapat dihapus!");
        }
        */

        rencanaPemeriksaanRestService.hapusRencanaPemeriksaan(rencanaPemeriksaanDTO.getId());
        response.setStatus(200);
        response.setMessage("success");
        response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " terhapus!");

        return response;
    }
}



