package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.Settings;
import com.ArgonautB04.SIRIO.rest.TugasPemeriksaanDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * @return daftar tugas pemeriksaan yang terhubung dengan rencana pemeriksaan tersebut
     */
    @GetMapping("/getByRencana/{idRencanaPemeriksaan}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukRencanaPemeriksaan(
            @PathVariable("idRencanaPemeriksaan") int idRencanaPemeriksaan
    ) {
        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.validateExistById(idRencanaPemeriksaan);
        List<TugasPemeriksaan> result = tugasPemeriksaanRestService.getByRencana(rencanaPemeriksaan);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil seluruh tugas pemeriksaan berdasarkan role employee
     *
     * @return daftar tugas pemeriksaan yang terhubung dengan employee tersebut
     */
    @GetMapping("/getByEmployee")
    private BaseResponse<List<TugasPemeriksaanDTO>> getAllTugasPemeriksaanUntukEmployee(
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        List<TugasPemeriksaan> daftarTugasPemeriksaan = employee.getRole() == roleRestService.getById(5) ?
                tugasPemeriksaanRestService.getByPelaksana(employee) : tugasPemeriksaanRestService.getAll();

        List<TugasPemeriksaanDTO> result = new ArrayList<>();
        for (TugasPemeriksaan tugasPemeriksaan : daftarTugasPemeriksaan) {
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

        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     *
     * @param idKantorCabang identifier kantor cabang
     * @return daftar tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     */
    @GetMapping("/getByKantorCabang/{idKantorCabang}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukKantorCabang(
            @PathVariable("idKantorCabang") int idKantorCabang
    ) {
        KantorCabang kantorCabang = kantorCabangRestService.validateExistById(idKantorCabang);
        List<TugasPemeriksaan> result = tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
        return new BaseResponse<>(200, "success", result);
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
        return new BaseResponse<>(200, "success", tugasPemeriksaanRestService.validateExistById(idTugasPemeriksaan));
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
        TugasPemeriksaan tugasPemeriksaanTemp = tugasPemeriksaanRestService.validateExistById(tugasPemeriksaanDTO.getId());

        tugasPemeriksaanTemp.setRencanaPemeriksaan(tugasPemeriksaanTemp.getRencanaPemeriksaan());
        tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
        tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));

        try {
            Date tanggalMulaiLocalDate = Settings.stringToDate(tugasPemeriksaanDTO.getTanggalMulai());
            Date tanggalSelesaiLocalDate = Settings.stringToDate(tugasPemeriksaanDTO.getTanggalSelesai());

            if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
                tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
                tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
            }
        } catch (ParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.EXPECTATION_FAILED,
                    "Terjadi kesalahan implementasi pada server"
            );
        }
        TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
        return new BaseResponse<>(200, "success", tugasPemeriksaan);
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
        tugasPemeriksaanRestService.validateExistById(tugasPemeriksaanDTO.getId());
        tugasPemeriksaanRestService.hapusTugasPemeriksaan(tugasPemeriksaanDTO.getId());
        return new BaseResponse<>(200, "success", "Tugas pemeriksaan dengan id " + tugasPemeriksaanDTO.getId() + " terhapus!");
    }

}