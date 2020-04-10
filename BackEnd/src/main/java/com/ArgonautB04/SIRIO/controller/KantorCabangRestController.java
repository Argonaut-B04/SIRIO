package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*")
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
     * Mengambil seluruh kantor cabang yang terhubung dengan user yang sedang login
     * <p>
     * Changelog:
     * - Mengubah filter id pembuat dengan filter logged in user
     *
     * @return daftar kantor cabang yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<KantorCabang>> getAllKantorCabang() {
        BaseResponse<List<KantorCabang>> response = new BaseResponse<>();
        List<KantorCabang> result = kantorCabangRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);


//        try {
//            Optional<Employee> employeeTarget = employeeRestService.getByUsername(principal.getName());
//            Employee employee;
//            if (employeeTarget.isPresent()) {
//                employee = employeeTarget.get();
//            } else {
//                throw new NoSuchElementException();
//            }
//            List<KantorCabang> result = kantorCabangRestService.getByPembuat(employee);
//            List<KantorCabangDTO> resultDTO = new ArrayList<>();
//            for (KantorCabang kantorCabang : result) {
//                KantorCabangDTO kantorCabangDTO = new KantorCabangDTO();
//                kantorCabangDTO.setId(kantorCabang.getIdKantor());
//                kantorCabangDTO.setArea(kantorCabang.getArea());
//                kantorCabangDTO.setRegional(kantorCabang.getRegional());
//                kantorCabangDTO.setIdPemilik(kantorCabang.getPemilik().getIdEmployee());
//                kantorCabangDTO.setKunjunganAudit(kantorCabang.getKunjunganAudit());
//                resultDTO.add(kantorCabangDTO);
//            }
//
//            response.setStatus(200);
//            response.setMessage("success");
//            response.setResult(resultDTO);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Akun anda tidak terdaftar atau tidak ditemukan!"
//            );
//        }
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
            @RequestBody KantorCabangDTO kantorCabangDTO,
            Principal principal
    ) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        KantorCabang kantorCabangTemp = new KantorCabang();

        try {
            Employee employee = employeeRestService.getByUsername(principal.getName()).get();
            kantorCabangTemp.setPembuat(employee);
            Employee pemilik = employeeRestService.getById(kantorCabangDTO.getIdPemilik());
            kantorCabangTemp.setPemilik(pemilik);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + kantorCabangDTO.getIdPembuat() + " tidak ditemukan!"
            );
        }

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabangTemp.setArea(kantorCabangDTO.getArea());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Area belum terisi!"
            );
        }

        if (kantorCabangDTO.getNamaKantorCabang() != null && !kantorCabangDTO.getNamaKantorCabang().equals("")) {
            if (kantorCabangRestService.getByNama(kantorCabangDTO.getNamaKantorCabang()) != null)
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Kantor Cabang " + kantorCabangDTO.getNamaKantorCabang() + " sudah ada pada database!"
                );
            kantorCabangTemp.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kantor Cabang tidak ditemukan!"
            );
        }

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabangTemp.setRegional(kantorCabangDTO.getRegional());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Regional belum terisi!"
            );
        }

        kantorCabangTemp.setKunjunganAudit(kantorCabangDTO.isKunjunganAudit());
        //kantorCabangTemp.setRiskRating(riskRatingRestService.getById(kantorCabangDTO.getIdRiskRating()));
        KantorCabang kantorCabang = kantorCabangRestService.buatKantorCabang(kantorCabangTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(kantorCabang);

        return response;

    }

    /**
     * Mengubah kantor cabang
     *
     * @param kantorCabangDTO data transfer object untuk kantor cabang yang akan diubah
     * @return kantor cabang yang telah disimpan perubahannya
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<KantorCabang> ubahKantorCabang(
            @RequestBody KantorCabangDTO kantorCabangDTO
    ) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();

        try{
            kantorCabangRestService.getById(kantorCabangDTO.getId());
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kantor Cabang dengan ID " + kantorCabangDTO.getId() + " tidak ditemukan!"
            );
        }

        KantorCabang kantorCabangTemp = kantorCabangRestService.getById(kantorCabangDTO.getId());

        try {
            Employee pembuat = employeeRestService.getById(kantorCabangDTO.getIdPembuat());
            kantorCabangTemp.setPembuat(pembuat);
            Employee pemilik = employeeRestService.getById(kantorCabangDTO.getIdPemilik());
            kantorCabangTemp.setPemilik(pemilik);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + kantorCabangDTO.getIdPembuat() + " tidak ditemukan!"
            );
        }

        kantorCabangTemp.setArea(kantorCabangDTO.getArea());
        kantorCabangTemp.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        kantorCabangTemp.setRegional(kantorCabangDTO.getRegional());
        kantorCabangTemp.setKunjunganAudit(kantorCabangDTO.isKunjunganAudit());
        kantorCabangTemp.setRiskRating(riskRatingRestService.getById(kantorCabangDTO.getIdRiskRating()));

        KantorCabang kantorCabang = kantorCabangRestService.ubahKantorCabang(kantorCabangDTO.getId(), kantorCabangTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(kantorCabang);

        return response;

    }

    /**
     * Menghapus kantor cabang
     *
     * @param kantorCabangDTO data transfer object untuk tugas pemeriksaan yang akan dihapus
     */
    @DeleteMapping("/hapus")
    private BaseResponse<String> hapusKantorCabang(
            @RequestBody KantorCabangDTO kantorCabangDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        try {
            kantorCabangRestService.hapusKantorCabang(kantorCabangDTO.getId());
            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Kantor cabang dengan id " + kantorCabangDTO.getId() + " terhapus!");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Kantor cabang dengan id " + kantorCabangDTO.getId() + " tidak dapat ditemukan");
        }
        return response;
    }
}
