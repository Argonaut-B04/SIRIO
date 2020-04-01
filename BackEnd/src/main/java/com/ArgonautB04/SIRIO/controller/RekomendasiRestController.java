package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.rest.Settings;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Rekomendasi")
public class RekomendasiRestController {

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    /**
     * Mengambil seluruh rekomendasi yang terhubung dengan user yang sedang login
     * <p>
     * Changelog:
     * - Mengubah filter id pembuat dengan filter logged in user
     *
     * @return daftar rekomendasi yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RekomendasiDTO>> getAllRekomendasiUntukLoggedInUser(Principal principal) {
        BaseResponse<List<RekomendasiDTO>> response = new BaseResponse<>();
        try {
            Optional<Employee> employeeTarget = employeeRestService.getByUsername(principal.getName());
            Employee employee;
            if (employeeTarget.isPresent()) {
                employee = employeeTarget.get();
            } else {
                throw new NoSuchElementException();
            }
            List<Rekomendasi> result = rekomendasiRestService.getByPembuat(employee);
            List<RekomendasiDTO> resultDTO = new ArrayList<>();
            for (Rekomendasi rekomendasi : result) {
                RekomendasiDTO rekomendasiDTO = new RekomendasiDTO();
                rekomendasiDTO.setId(rekomendasi.getIdRekomendasi());
                rekomendasiDTO.setKeterangan(rekomendasi.getKeterangan());
                Date tenggatWaktu = rekomendasi.getTenggatWaktu();
                if (tenggatWaktu != null) {
                    rekomendasiDTO.setTenggatWaktu(tenggatWaktu.toString());
                }
                rekomendasiDTO.setStatus(rekomendasi.getStatusRekomendasi().getNamaStatus());
                resultDTO.add(rekomendasiDTO);
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
     * Mengambil seluruh rekomendasi yang terhubung dengan suatu kantor cabang
     *
     * @param idKantor identifier kantor cabang
     * @return daftar rekomendasi yang terhubung dengan kantor cabang
     */
    @GetMapping("/getAllByKantorCabang/{idKantor}")
    private BaseResponse<List<Rekomendasi>> getAllRekomendasiUntukKantorCabang(
            @PathVariable("idKantor") int idKantor
    ) {
        BaseResponse<List<Rekomendasi>> response = new BaseResponse<>();
        try {
            KantorCabang kantorCabang = kantorCabangRestService.getById(idKantor);
            List<TugasPemeriksaan> daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
            List<HasilPemeriksaan> daftarHasilPemeriksaan = hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            List<KomponenPemeriksaan> daftarKomponenPemeriksaan = komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            List<Rekomendasi> result = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kantor Cabang dengan ID " + idKantor + " tidak ditemukan!"
            );
        }
        return response;
    }


    /**
     * Mengubah tenggat waktu untuk rekomendasi spesifik. (UNTESTED)
     *
     * @return objek rekomendasi yang telah diubah
     */
    @PostMapping("/tenggatWaktu")
    private BaseResponse<Rekomendasi> ubahTenggatWaktu(
            @RequestBody RekomendasiDTO rekomendasiDTO
    ) {
        BaseResponse<Rekomendasi> response = new BaseResponse<>();
        Integer idRekomendasi = rekomendasiDTO.getId();
        try {
            Rekomendasi result = rekomendasiRestService.ubahTenggatWaktu(
                    idRekomendasi,
                    Settings.convertToDateViaInstant(rekomendasiDTO.getTenggatWaktuDate())
            );

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        } catch (IllegalAccessError e) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED, "Tenggat waktu rekomendasi belum dapat diatur!"
            );
        }
        return response;
    }
}
