package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Autowired
    private RoleRestService roleRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    /**
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan suatu rencana pemeriksaan
     *
     * @param idRencanaPemeriksaan identifier rencana pemeriksaan
     *
     * @return daftar tugas pemeriksaan yang terhubung dengan rencana pemeriksaan tersebut
     */
    @GetMapping("/getByRencana/{idRencanaPemeriksaan}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukRencanaPemeriksaan(
            @PathVariable("idRencanaPemeriksaan") int idRencanaPemeriksaan
    ) {
        BaseResponse<List<TugasPemeriksaan>> response = new BaseResponse<>();
        try {
            RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.getById(idRencanaPemeriksaan);
            List<TugasPemeriksaan> result = tugasPemeriksaanRestService.getByRencana(rencanaPemeriksaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rencana dengan ID " + idRencanaPemeriksaan + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Mengambil seluruh tugas pemeriksaan berdasarkan role employee
     *
     * @return daftar tugas pemeriksaan yang terhubung dengan employee tersebut
     */
    @GetMapping("/getByEmployee")
    private BaseResponse<List<TugasPemeriksaanDTO>> getAllTugasPemeriksaanUntukEmployee(
            Principal principal, ModelMap model
    ) {
        BaseResponse<List<TugasPemeriksaanDTO>> response = new BaseResponse<>();
        Employee employee = employeeRestService.getByUsername(principal.getName()).get();

        List<TugasPemeriksaan> daftarTugasPemeriksaan = employee.getRole() == roleRestService.getById(6) ?
                tugasPemeriksaanRestService.getByPelaksana(employee) : tugasPemeriksaanRestService.getAll();

        List<TugasPemeriksaanDTO> result = new ArrayList<>();
        for (TugasPemeriksaan tugasPemeriksaan: daftarTugasPemeriksaan) {
            TugasPemeriksaanDTO tugasPemeriksaanDTO = new TugasPemeriksaanDTO();
            tugasPemeriksaanDTO.setId(tugasPemeriksaan.getIdTugas());
            tugasPemeriksaanDTO.setTanggalMulai(tugasPemeriksaan.getTanggalMulai().toString());
            tugasPemeriksaanDTO.setTanggalSelesai(tugasPemeriksaan.getTanggalSelesai().toString());
            tugasPemeriksaanDTO.setIdQA(tugasPemeriksaan.getPelaksana().getIdEmployee());
            tugasPemeriksaanDTO.setNamaQA(tugasPemeriksaan.getPelaksana().getNama());
            tugasPemeriksaanDTO.setNamaKantorCabang(tugasPemeriksaan.getKantorCabang().getNamaKantor());
            hasilPemeriksaanRestService.getByTugasPemeriksaan(tugasPemeriksaan).ifPresent(
                    pemeriksaan -> tugasPemeriksaanDTO.setIdHasilPemeriksaan(pemeriksaan.getIdHasilPemeriksaan()));
            result.add(tugasPemeriksaanDTO);
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     *
     * @param idKantorCabang identifier kantor cabang
     *
     * @return daftar tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     */
    @GetMapping("/getByKantorCabang/{idKantorCabang}")
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

                tugasPemeriksaanTemp.setRencanaPemeriksaan(tugasPemeriksaanTemp.getRencanaPemeriksaan());
                tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
                tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));

                Date tanggalMulaiLocalDate = Settings.stringToDate(tugasPemeriksaanDTO.getTanggalMulai());
                Date tanggalSelesaiLocalDate = Settings.stringToDate(tugasPemeriksaanDTO.getTanggalSelesai());

                if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
                    tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
                    tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
                }

                TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(tugasPemeriksaan);

        } catch (NoSuchElementException | ParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tugas Pemeriksaan dengan ID " + tugasPemeriksaanDTO.getId() + " tidak ditemukan!"
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