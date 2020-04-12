package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;
import com.ArgonautB04.SIRIO.services.KomponenPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.RisikoRestService;
import com.ArgonautB04.SIRIO.services.SOPRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/Risiko")
public class RisikoRestController {

    @Autowired
    private RisikoRestService risikoRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private SOPRestService sopRestService;

    /**
     * Menambahkan risiko baru.
     * @param risikoDTO data transfer object untuk risiko yang akan ditambah
     * @return menyimpan risiko baru ke db
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<Risiko> tambahRisiko(
            @RequestBody RisikoDTO risikoDTO
    ) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        Risiko risikoTemp = new Risiko();
//        risikoTemp.setChildList(risikoDTO.getChild());
//        risikoTemp.setKomponen(risikoDTO.getKomponen());
//        risikoTemp.setRisikoKategori(risikoDTO.getKategori());
//        risikoTemp.setNamaRisiko(risikoDTO.getNama());
//        if (risikoDTO.getParent() != 0) {
//            risikoTemp.setParent(risikoRestService.getById(risikoDTO.getId()));
//        } else {
//            risikoTemp.setParent(null);
//        }
//        try {
//            SOP sop = sopRestService.getById(risikoDTO.getSop());
//            risikoTemp.setSop(sop);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "SOP dengan ID " + risikoDTO.getSop() + " tidak ditemukan!"
//            );
//        }
        risikoTemp= risikoRestService.transformasidto(risikoTemp, risikoDTO);

        Risiko risiko = risikoRestService.buatRisiko(risikoTemp);
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(risiko);

        return response;
    }

    /**
     * Menampilkan satu risiko.
     * @param idRisiko
     * @return object risiko beserta atribut yang sesuai dengan idRisiko tersebut
     */
    @GetMapping(value = "/{idRisiko}")
    private BaseResponse<Risiko> getRisiko(@PathVariable("idRisiko") int idRisiko) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        try {
            Risiko result = risikoRestService.getById(idRisiko);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ID Risiko " + String.valueOf(idRisiko) + " Tidak Ditemukan");
        }
        return response;
    }

    /**
     * Menghapus satu risiko.
     * @param risikoDTO data transfer object untuk risiko yang akan dihapus
     * @return risiko dengan idRisiko tersebut dihapus dari db
     */
    @PostMapping(value = "/hapus")
    private BaseResponse<String> hapusRisiko(@RequestBody RisikoDTO risikoDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        Risiko risiko;
        try {
            risiko = risikoRestService.getById(risikoDTO.getId());
            if (komponenPemeriksaanRestService.getByRisiko(risiko) != null) {
                response.setStatus(403);
                response.setMessage("failed");
                risikoRestService.nonaktifkanRisiko(risiko.getIdRisiko());
                response.setResult("Risiko dengan id " + risiko.getIdRisiko() + " dinonaktifkan!");
                return response;
            } else {
                risikoRestService.hapusRisiko(risiko.getIdRisiko());
            }
        } catch (NoSuchElementException | NullPointerException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Risiko dengan id " + risikoDTO.getId() + " tidak dapat ditemukan");
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult("Risiko dengan id " + risikoDTO.getId() + " terhapus!");
        return response;
    }

    /**
     * Mengubah risiko, semua atribut dapat diubah.
     * @param risikoDTO data transfer object untuk risiko yang akan diubah
     * @return perubahan data akan disimpan di db
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<Risiko> ubahRisiko(
            @RequestBody RisikoDTO risikoDTO
    ) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        try {
        Risiko risikoTemp = risikoRestService.getById(risikoDTO.getId());
        risikoTemp = risikoRestService.transformasidto(risikoTemp, risikoDTO);
        Risiko result = risikoRestService.ubahRisiko(risikoTemp.getIdRisiko(), risikoTemp);

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ID Risiko " + risikoDTO.getId() + " Tidak Ditemukan");
        }

        return response;

    }

    /**
     * Menampilkan daftar risiko-risiko yang sudah ada.
     * @return daftar risiko berupa list.
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RisikoDTO>> getAllRisiko() {
        BaseResponse<List<RisikoDTO>> response = new BaseResponse<>();
        List<Risiko> daftarRisiko = risikoRestService.getAll();
        List<RisikoDTO> daftarRisikoDTO = new ArrayList<>();
        for (Risiko risiko : daftarRisiko) {
            RisikoDTO risikoDTO = new RisikoDTO();
            risikoDTO.setId(risiko.getIdRisiko());
            risikoDTO.setNama(risiko.getNamaRisiko());
            risikoDTO.setKategori(risiko.getRisikoKategori());
            risikoDTO.setSop(risiko.getSop().getIdSop());
            if (risiko.getKomponen() == null || risiko.getKomponen().equals("")) {
                risikoDTO.setKomponen(null);
            } else {
                risikoDTO.setKomponen(risiko.getKomponen());
            }
            if (risiko.getParent() != null) {
                risikoDTO.setParent(risiko.getParent().getIdRisiko());
                risikoDTO.setNamaParent(risiko.getParent().getNamaRisiko());
            }
            daftarRisikoDTO.add(risikoDTO);
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(daftarRisikoDTO);
        return response;
    }

    /**
     * Mengambil daftar risiko di kategori 3
     *
     * @return daftar detail risiko di kategori 3
     */
    @GetMapping("/getAll/child")
    private BaseResponse<List<RisikoDTO>> getAllRisikoChild() {
        BaseResponse<List<RisikoDTO>> response = new BaseResponse<>();
        List<RisikoDTO> result = new ArrayList<>();

        List<Risiko> daftarRisiko = risikoRestService.getByKategori(3);
        for (Risiko risiko: daftarRisiko) {
            RisikoDTO risikoDTO = new RisikoDTO();
            risikoDTO.setId(risiko.getIdRisiko());
            risikoDTO.setNama(risiko.getNamaRisiko());
            risikoDTO.setKomponen(risiko.getKomponen());
            if (risiko.getParent() != null) {
                risikoDTO.setParent(risiko.getParent().getIdRisiko());
                if (risiko.getParent().getParent() != null)
                    risikoDTO.setGrantParent(risiko.getParent().getParent().getIdRisiko());
            }
            risikoDTO.setLinkSop(risiko.getSop().getLinkDokumen());
            risikoDTO.setNamaSop(risiko.getSop().getJudul());
            result.add(risikoDTO);
        }

        response.setResult(result);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }

    @PostMapping("/ubah-hierarki")
    private BaseResponse<List<Risiko>> ubahHierarkiRisiko(
            @RequestBody List<Risiko> risikoList
    ) {
        BaseResponse<List<Risiko>> response = new BaseResponse<>();
        try {
            List<Risiko> listRisikoBaru = new ArrayList<>();
            for (Risiko risk : risikoList) {
                if (risikoRestService.isExistInDatabase(risk)) {
                    Risiko risikoAwal = risikoRestService.getById(risk.getIdRisiko());
                    risikoRestService.ubahHierarki(risikoAwal, risk);
                    listRisikoBaru.add(risikoAwal);
                } else {
                    throw new NoSuchElementException("risiko yang hierarkinya akan diubah tidak ditemukan.");
                }
            }

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(listRisikoBaru);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Konfigurasi Risk Level gagal"
            );
        }
        return response;
    }

    @GetMapping("/ubah-hierarki/getParent/{idRisiko}")
    private BaseResponse<List<Risiko>> getParent(@PathVariable("idRisiko") int idRisiko) {
        BaseResponse<List<Risiko>> response = new BaseResponse<>();
        try {
            Risiko risk = risikoRestService.getById(idRisiko);
            List<Risiko> result = risikoRestService.getParentByKategori(risk.getRisikoKategori());
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            response.setMessage("kategori tidak ditemukan");
        }
        return response;
    }
}
