package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.model.TemuanRisiko;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.TemuanRisikoDTO;
import com.ArgonautB04.SIRIO.services.HasilPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.RisikoRestService;
import com.ArgonautB04.SIRIO.services.TemuanRisikoRestService;
import com.ArgonautB04.SIRIO.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/TemuanRisiko")
public class TemuanRisikoRestController {

    @Autowired
    private TemuanRisikoRestService temuanRisikoRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private RisikoRestService risikoRestService;

    /**
     * Mengambil histori temuan risiko pada suatu kantor cabang
     *
     * @param idTugasPemeriksaan identifier tugas pemeriksaan
     * @return daftar temuan risiko pada suatu kantor cabang
     */
    @GetMapping("/{idTugasPemeriksaan}")
    private BaseResponse<List<TemuanRisikoDTO>> getHistoriTemuanRisikoKantorCabang(
            @PathVariable("idTugasPemeriksaan") int idTugasPemeriksaan
    ) {
        BaseResponse<List<TemuanRisikoDTO>> response = new BaseResponse<>();
        List<TemuanRisikoDTO> result = new ArrayList<>();

        TugasPemeriksaan tugasPemeriksaan;
        try {
            tugasPemeriksaan = tugasPemeriksaanRestService.getById(idTugasPemeriksaan);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tugas Pemeriksaan dengan ID " + idTugasPemeriksaan + " tidak ditemukan!"
            );
        }

        List<Risiko> daftarRisiko = risikoRestService.getByKategori(3);
        for (Risiko risiko : daftarRisiko) {
            List<TemuanRisiko> daftarTemuanRisiko = temuanRisikoRestService.getHistoriTemuanRisikoKantorCabang(
                    tugasPemeriksaan, risiko);
            for (TemuanRisiko temuanRisiko: daftarTemuanRisiko) {
                TemuanRisikoDTO temuanRisikoDTO = new TemuanRisikoDTO();
                temuanRisikoDTO.setKeterangan(temuanRisiko.getKeterangan());
                temuanRisikoDTO.setIdRisiko(risiko.getIdRisiko());
                result.add(temuanRisikoDTO);
            }
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }
}
