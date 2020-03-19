package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/TugasPemeriksaan")
public class TugasPemeriksaanRestController {

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private RencanaPemeriksaanRestService rencanaPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

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

    /**
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     *
     * @param idKantorCabang identifier pelaksanan
     *
     * @return daftar tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     */
    @GetMapping("/getAll/{idKantorCabang}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukKantorCabang(
            @PathVariable("idKantorCabang") int idKantorCabang
    ) {
        BaseResponse<List<TugasPemeriksaan>> response = new BaseResponse<>();
        try {
            KantorCabang kantorCabang = kantorCabangRestService.getById(idKantorCabang);
            List<TugasPemeriksaan> result = tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kantor Cabang dengan ID " + idKantorCabang + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Mengambil suatu tugas pemeriksaan
     *
     * @param idTugasPemeriksaan identifier tugas pemeriksaan
     * @return detail tugas pemeriksaan
     */
    @GetMapping("/{idTugasPemeriksaan}")
    private BaseResponse<TugasPemeriksaan> getTugasPemeriksaan(
            @PathVariable("idTugasPemeriksaan") int idTugasPemeriksaan
    ) {
        BaseResponse<TugasPemeriksaan> response = new BaseResponse<>();
        try {
            TugasPemeriksaan result = tugasPemeriksaanRestService.getById(idTugasPemeriksaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tugas Pemeriksaan dengan ID " + idTugasPemeriksaan + " tidak ditemukan!"
            );
        }
        return response;
    }

//    /**
//     * Menambah tugas pemeriksaan baru untuk rencana pemeriksaan spesifik
//     *
//     * @param tugasPemeriksaanDTO data transfer object untuk tugas pemeriksaan yang akan ditambah
//     * @return tugas pemeriksaan yang telah disimpan
//     */
//    @PostMapping(value = "/tambah", consumes = {"application/json"})
//    private BaseResponse<TugasPemeriksaan> tambahTugasPemeriksaan(
//            @RequestBody TugasPemeriksaanDTO tugasPemeriksaanDTO
//    ) {
//        BaseResponse<TugasPemeriksaan> response = new BaseResponse<>();
//        TugasPemeriksaan tugasPemeriksaanTemp = new TugasPemeriksaan();
//
//        try {
//            RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.getById(tugasPemeriksaanDTO.getIdRencanaPemeriksaan());
//            tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaan);
//        }catch (EmptyResultDataAccessException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Rencana Pemeriksaan dengan ID " + tugasPemeriksaanDTO.getIdRencanaPemeriksaan() + " tidak ditemukan!"
//            );
//        }
//
//        tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
//        tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));
//
//        LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
//        tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
//
//        LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());
//        tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
//
//        TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.buatTugasPemeriksaan(tugasPemeriksaanTemp);
//
//        response.setStatus(200);
//        response.setMessage("success");
//        response.setResult(tugasPemeriksaan);
//
//        return response;
//    }

    /**
     * Mengubah tugas pemeriksaan
     *
     * @param tugasPemeriksaanDTO data transfer object untuk tugas pemeriksaan yang akan diubah
     * @return tugas pemeriksaan yang telah disimpan perubahannya
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<TugasPemeriksaan> ubahTugasPemeriksaan(
            @RequestBody TugasPemeriksaanDTO tugasPemeriksaanDTO
    ) {
        BaseResponse<TugasPemeriksaan> response = new BaseResponse<>();
        try {
            TugasPemeriksaan tugasPemeriksaanTemp = tugasPemeriksaanRestService.getById(tugasPemeriksaanDTO.getId());
            TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(tugasPemeriksaan);

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tugas pemeriksaan dengan ID " + tugasPemeriksaanDTO.getId() + " tidak ditemukan!"
            );
        }

        return response;
    }

    /**
     * Menghapus tugas pemeriksaan
     *
     * @param tugasPemeriksaanDTO data transfer object untuk tugas pemeriksaan yang akan dihapus
     */
    @DeleteMapping("/hapus")
    private BaseResponse<String> hapusTugasPemeriksaan(
            @RequestBody TugasPemeriksaanDTO tugasPemeriksaanDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            tugasPemeriksaanRestService.hapusTugasPemeriksaan(tugasPemeriksaanDTO.getId());
            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Tugas pemeriksaan dengan id " + tugasPemeriksaanDTO.getId() + " terhapus!");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Tugas pemeriksaan dengan id " + tugasPemeriksaanDTO.getId() + " tidak dapat ditemukan");
        }
        return response;
    }

}