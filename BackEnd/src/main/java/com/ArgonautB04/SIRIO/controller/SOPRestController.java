package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.SOPRestService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ArgonautB04.SIRIO.rest.SOPDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/SOP")
public class SOPRestController {

    @Autowired
    private SOPRestService sopRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Mengambil seluruh SOP yang terhubung dengan user yang sedang login
     *
     * @return daftar SOP yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<SOP>> getAllSop() {
        return new BaseResponse<>(200, "success", sopRestService.getAll());
    }

    /**
     * Mengecek apakah judul SOP yang ditambahkan sudah ada di database
     *
     * @return true jika sudah ada
     */
    @GetMapping("/check/{judulSOP}")
    private BaseResponse<Boolean> isExistInDatabase(
            @PathVariable("judulSOP") String judulSOP
    ) {
        Optional<SOP> sop = sopRestService.getByJudul(judulSOP);
        return new BaseResponse<>(200, "success", sop.isPresent());
    }

    /**
     * Mengambil suatu sop
     *
     * @param idSOP identifier sop
     * @return detail sop
     */
    @GetMapping("/{idSOP}")
    private BaseResponse<SOP> getSOP(
            @PathVariable("idSOP") int idSOP
    ) {
        BaseResponse<SOP> response = new BaseResponse<>();
        SOP result = sopRestService.validateExistById(idSOP);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Menambah SOP baru
     *
     * @param sopDTO data transfer object untuk sop yang akan ditambah
     * @return sop yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<SOP> tambahSOP(
            @RequestBody SOPDTO sopDTO,
            Principal principal
    ) {
        BaseResponse<SOP> response = new BaseResponse<>();
        SOP sopTemp = new SOP();

        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        sopTemp.setPembuat(employee);

        if (sopDTO.getJudul() != null && !sopDTO.getJudul().equals("")) {
            if (sopRestService.getByJudul(sopDTO.getJudul()).isPresent())
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "SOP " + sopDTO.getJudul() + " sudah ada pada database!"
                );
            sopTemp.setJudul(sopDTO.getJudul());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Judul belum terisi!"
            );
        }

        if (sopDTO.getKategori() != null && !sopDTO.getKategori().equals("")) {
            sopTemp.setKategori(sopDTO.getKategori());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kategori belum diisi!"
            );
        }

        if (sopDTO.getLinkDokumen() != null && !sopDTO.getLinkDokumen().equals("")) {
            sopTemp.setLinkDokumen(sopDTO.getLinkDokumen());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link dokumen belum terisi!"
            );
        }

        SOP sop = sopRestService.buatSOP(sopTemp);
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(sop);

        return response;

    }

    /**
     * Mengubah SOP
     *
     * @param sopDTO data transfer object untuk sop yang akan diubah
     * @return sop yang telah disimpan perubahannya
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<SOP> ubahSOP(
            @RequestBody SOPDTO sopDTO
    ) {
        BaseResponse<SOP> response = new BaseResponse<>();

        SOP sop = sopRestService.validateExistById(sopDTO.getId());

        if (sopDTO.getJudul() != null && !sopDTO.getJudul().equals("")) {
            sop.setJudul(sopDTO.getJudul());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Judul belum diisi!"
            );
        }

        if (sopDTO.getKategori() != null && !sopDTO.getKategori().equals("")) {
            sop.setKategori(sopDTO.getKategori());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kategori belum diisi!"
            );
        }

        if (sopDTO.getLinkDokumen() != null && !sopDTO.getLinkDokumen().equals("")) {
            sop.setLinkDokumen(sopDTO.getLinkDokumen());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link dokumen belum terisi!"
            );
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(sopRestService.ubahSOP(sopDTO.getId(), sop));
        return response;

    }

    /**
     * Menghapus SOP
     *
     * @param sopDTO data transfer object untuk sop yang akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusSOP(
            @RequestBody SOPDTO sopDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();

        SOP sop = sopRestService.validateExistById(sopDTO.getId());

        response.setStatus(200);
        response.setMessage("success");

        try {
            sopRestService.hapusSOP(sop.getIdSop());
        } catch (DataIntegrityViolationException e) {
            sopRestService.nonaktifkanSOP(sop.getIdSop());
            response.setResult("SOP dengan id " + sopDTO.getId() + " dinonaktifkan!");
            return response;
        }

        response.setResult("SOP dengan id " + sopDTO.getId() + " terhapus!");
        return response;
    }
}
