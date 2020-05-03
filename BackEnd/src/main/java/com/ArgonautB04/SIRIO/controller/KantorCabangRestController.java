package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.KantorCabangDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.KantorCabangRestService;
import com.ArgonautB04.SIRIO.services.RiskRatingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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
        return new BaseResponse<>(200, "success", kantorCabangRestService.getAll());
    }

    @GetMapping("/check/{namaKantor}")
    private BaseResponse<Boolean> isExistInDatabase(
            @PathVariable("namaKantor") String namaKantor
    ) {
        Optional<KantorCabang> kantorCabang = kantorCabangRestService.getByNama(namaKantor);
        return new BaseResponse<>(200, "success", kantorCabang.isPresent());
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
        KantorCabang result = kantorCabangRestService.validateExistById(idKantorCabang);
        return new BaseResponse<>(200, "success", result);
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

        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        kantorCabangTemp.setPembuat(employee);
        Employee pemilik = employeeRestService.validateEmployeeExistById(kantorCabangDTO.getIdPemilik());
        kantorCabangTemp.setPemilik(pemilik);

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabangTemp.setArea(kantorCabangDTO.getArea());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Area belum terisi!"
            );
        }

        if (kantorCabangDTO.getNamaKantorCabang() != null && !kantorCabangDTO.getNamaKantorCabang().equals("")) {
            if (kantorCabangRestService.getByNama(kantorCabangDTO.getNamaKantorCabang()).isPresent())
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Kantor Cabang " + kantorCabangDTO.getNamaKantorCabang() + " sudah ada pada database!"
                );
            kantorCabangTemp.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kantor Cabang belum diisi!"
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
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<KantorCabang> ubahKantorCabang(
            @RequestBody KantorCabangDTO kantorCabangDTO
    ) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();

        KantorCabang kantorCabang = kantorCabangRestService.validateExistById(kantorCabangDTO.getId());

        if (kantorCabangDTO.getNamaKantorCabang() != null && !kantorCabangDTO.getNamaKantorCabang().equals("")) {
            kantorCabang.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nama kantor belum diisi!"
            );
        }

//        if (kantorCabangDTO.getNamaKantorCabang() != null && !kantorCabangDTO.getNamaKantorCabang().equals("")) {
//            if (kantorCabangRestService.getByNama(kantorCabangDTO.getNamaKantorCabang()).isPresent())
//                throw new ResponseStatusException(
//                        HttpStatus.CONFLICT, "Kantor Cabang " + kantorCabangDTO.getNamaKantorCabang() + " sudah ada pada database!"
//                );
//            kantorCabang.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Kantor Cabang belum diisi!"
//            );
//        }

        if (kantorCabangDTO.getRegional() != null && !kantorCabangDTO.getRegional().equals("")) {
            kantorCabang.setRegional(kantorCabangDTO.getRegional());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Regional belum diisi!"
            );
        }

        kantorCabang.setKunjunganAudit(kantorCabangDTO.isKunjunganAudit());

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabang.setArea(kantorCabangDTO.getArea());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Area belum diisi!"
            );
        }

        Employee employee = employeeRestService.validateEmployeeExistById(kantorCabangDTO.getIdPemilik());
        kantorCabang.setPemilik(employee);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(kantorCabangRestService.ubahKantorCabang(kantorCabangDTO.getId(), kantorCabang));
        return response;

    }

    /**
     * Menghapus kantor cabang
     *
     * @param kantorCabangDTO data transfer object untuk tugas pemeriksaan yang akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusKantorCabang(
            @RequestBody KantorCabangDTO kantorCabangDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();

        KantorCabang kantorCabang = kantorCabangRestService.validateExistById(kantorCabangDTO.getId());

        response.setStatus(200);
        response.setMessage("success");

        try {
            kantorCabangRestService.hapusKantorCabang(kantorCabang.getIdKantor());
        } catch (DataIntegrityViolationException e) {
            kantorCabangRestService.nonaktifkanKantor(kantorCabang.getIdKantor());
            response.setResult("Kantor Cabang dengan id " + kantorCabangDTO.getId() + " dinonaktifkan!");
            return response;
        }

        response.setResult("Kantor Cabang dengan id " + kantorCabangDTO.getId() + " terhapus!");
        return response;
    }
}
