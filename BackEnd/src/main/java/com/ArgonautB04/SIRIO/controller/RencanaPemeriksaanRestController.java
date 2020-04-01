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

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
     * Mengambil seluruh rencana pemeriksaan yang terhubung dengan user yang sedang login
     * <p>
     * Changelog:
     * - Mengubah filter id pembuat dengan filter logged in user
     *
     * @return daftar rencana pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RencanaPemeriksaanDTO>> getAllRencanaPemeriksaan(Principal principal) {
        BaseResponse<List<RencanaPemeriksaanDTO>> response = new BaseResponse<>();

        try {
            Optional<Employee> employeeTarget = employeeRestService.getByUsername(principal.getName());
            Employee employee;
            if (employeeTarget.isPresent()) {
                employee = employeeTarget.get();
            } else {
                throw new NoSuchElementException();
            }
            List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getByPembuat(employee);
            List<RencanaPemeriksaanDTO> resultDTO = new ArrayList<>();
            for (RencanaPemeriksaan rencanaPemeriksaan : result) {
                RencanaPemeriksaanDTO rencanaPemeriksaanDTO = new RencanaPemeriksaanDTO();
                rencanaPemeriksaanDTO.setId(rencanaPemeriksaan.getIdRencana());
                rencanaPemeriksaanDTO.setStatus(rencanaPemeriksaan.getStatus().getIdStatusRencana());
                rencanaPemeriksaanDTO.setNamaRencana(rencanaPemeriksaan.getNamaRencana());

                resultDTO.add(rencanaPemeriksaanDTO);
            }

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(resultDTO);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Akun anda tidak terdaftar atau tidak ditemukan!"
            );
        }
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

            tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaan);
            tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
            tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));

            LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
            LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());

            if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
                tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
                tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
            }

            tugasPemeriksaanRestService.buatTugasPemeriksaan(tugasPemeriksaanTemp);
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

        try {
            RencanaPemeriksaan rencanaPemeriksaanTemp = rencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getId());

            rencanaPemeriksaanTemp.setStatus(statusRencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getStatus()));
            rencanaPemeriksaanTemp.setNamaRencana(rencanaPemeriksaanDTO.getNamaRencana());
            rencanaPemeriksaanTemp.setLinkMajelis(rencanaPemeriksaanDTO.getLinkMajelis());
            rencanaPemeriksaanTemp.setPembuat(employeeRestService.getById(rencanaPemeriksaanDTO.getIdPembuat()));

            RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.ubahRencanaPemeriksaan(rencanaPemeriksaanDTO.getId(), rencanaPemeriksaanTemp);

            for (TugasPemeriksaanDTO tugasPemeriksaanDTO : rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()) {
                TugasPemeriksaan tugasPemeriksaanTemp = tugasPemeriksaanRestService.getById(tugasPemeriksaanDTO.getId());

                tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaan);
                tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
                tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));

                LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
                LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());

                if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
                    tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
                    tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
                }

                tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
            }

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(rencanaPemeriksaan);
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rencana Pemeriksaan dengan ID " + rencanaPemeriksaanDTO.getId() + " tidak ditemukan!"
            );
        }

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

        //Tidak dapat dihapus jika rencana sudah dijalankan
        if(rencanaPemeriksaanDTO.getStatus() > 1){
            response.setStatus(500);
            response.setMessage("error");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " tidak dapat dihapus!");
        }else {
            rencanaPemeriksaanRestService.hapusRencanaPemeriksaan(rencanaPemeriksaanDTO.getId());
            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " terhapus!");
        }

        return response;
    }
}



