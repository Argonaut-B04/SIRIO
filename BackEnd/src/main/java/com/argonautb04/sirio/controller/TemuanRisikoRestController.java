package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Risiko;
import com.argonautb04.sirio.model.TemuanRisiko;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.rest.TemuanRisikoDTO;
import com.argonautb04.sirio.services.RisikoRestService;
import com.argonautb04.sirio.services.TemuanRisikoRestService;
import com.argonautb04.sirio.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
            @PathVariable("idTugasPemeriksaan") int idTugasPemeriksaan) {
        List<TemuanRisikoDTO> result = new ArrayList<>();

        TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService.validateExistById(idTugasPemeriksaan);

        List<Risiko> daftarRisiko = risikoRestService.getByKategori(3);
        for (Risiko risiko : daftarRisiko) {
            List<TemuanRisiko> daftarTemuanRisiko = temuanRisikoRestService
                    .getHistoriTemuanRisikoKantorCabang(tugasPemeriksaan, risiko);
            for (TemuanRisiko temuanRisiko : daftarTemuanRisiko) {
                TemuanRisikoDTO temuanRisikoDTO = new TemuanRisikoDTO();
                temuanRisikoDTO.setKeterangan(temuanRisiko.getKeterangan());
                temuanRisikoDTO.setIdRisiko(risiko.getIdRisiko());
                result.add(temuanRisikoDTO);
            }
        }

        return new BaseResponse<>(200, "success", result);
    }
}
