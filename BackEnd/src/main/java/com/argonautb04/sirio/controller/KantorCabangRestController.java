package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KantorCabang;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.rest.KantorCabangDTO;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.KantorCabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/KantorCabang")
public class KantorCabangRestController {

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Mengambil seluruh kantor cabang
     *
     * @return daftar kantor cabang yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<KantorCabang>> getAllKantorCabang() {
        return new BaseResponse<>(200, "success", kantorCabangRestService.getAll());
    }

    /**
     * Mengecek apakah nama kantor cabang yang ditambahkan sudah ada di database
     *
     * @param namaKantor
     * @return true jika sudah ada
     */
    @GetMapping("/check/{namaKantor}")
    private BaseResponse<Boolean> isExistInDatabase(@PathVariable("namaKantor") final String namaKantor) {
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
    private BaseResponse<KantorCabang> getKantorCabang(@PathVariable("idKantorCabang") int idKantorCabang) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        KantorCabang result = kantorCabangRestService.validateExistById(idKantorCabang);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil suatu kantor cabang
     *
     * @return detail kantor cabang
     */
    @GetMapping("/pemilik")
    private BaseResponse<KantorCabang> getKantorCabangbyPemilik(Principal principal) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        KantorCabang result = kantorCabangRestService
                .getByPemilik(employeeRestService.validateEmployeeExistByPrincipal(principal));
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Menambah kantor cabang baru
     *
     * @param principal
     * @param kantorCabangDTO data transfer object untuk kantor cabang yang akan
     *                        ditambah
     * @return kantor cabang yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<KantorCabang> tambahKantorCabang(@RequestBody final KantorCabangDTO kantorCabangDTO,
                                                          final Principal principal) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        KantorCabang kantorCabangTemp = new KantorCabang();

        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        kantorCabangTemp.setPembuat(employee);
        Employee pemilik = employeeRestService.validateEmployeeExistById(kantorCabangDTO.getIdPemilik());
        kantorCabangTemp.setPemilik(pemilik);

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabangTemp.setArea(kantorCabangDTO.getArea());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Area belum terisi!");
        }

        if (kantorCabangDTO.getNamaKantorCabang() != null && !kantorCabangDTO.getNamaKantorCabang().equals("")) {
            if (kantorCabangRestService.getByNama(kantorCabangDTO.getNamaKantorCabang()).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Kantor Cabang " + kantorCabangDTO.getNamaKantorCabang() + " sudah ada pada database!");
            kantorCabangTemp.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kantor Cabang belum diisi!");
        }

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabangTemp.setRegional(kantorCabangDTO.getRegional());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Regional belum terisi!");
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
     * @param kantorCabangDTO data transfer object untuk kantor cabang yang akan
     *                        diubah
     * @return kantor cabang yang telah disimpan perubahannya
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<KantorCabang> ubahKantorCabang(@RequestBody final KantorCabangDTO kantorCabangDTO) {
        BaseResponse<KantorCabang> response = new BaseResponse<>();
        KantorCabang kantorCabang = kantorCabangRestService.validateExistById(kantorCabangDTO.getId());

        if (kantorCabangDTO.getNamaKantorCabang() != null && !kantorCabangDTO.getNamaKantorCabang().equals("")) {
            kantorCabang.setNamaKantor(kantorCabangDTO.getNamaKantorCabang());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nama kantor belum diisi!");
        }

        if (kantorCabangDTO.getRegional() != null && !kantorCabangDTO.getRegional().equals("")) {
            kantorCabang.setRegional(kantorCabangDTO.getRegional());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Regional belum diisi!");
        }

        kantorCabang.setKunjunganAudit(kantorCabangDTO.isKunjunganAudit());

        if (kantorCabangDTO.getArea() != null && !kantorCabangDTO.getArea().equals("")) {
            kantorCabang.setArea(kantorCabangDTO.getArea());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Area belum diisi!");
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
     * @param kantorCabangDTO data transfer object untuk kantor cabang yang akan
     *                        dihapus
     * @return pemberitahuan bahwa kantor cabang berhasil telah dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusKantorCabang(@RequestBody final KantorCabangDTO kantorCabangDTO) {
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