package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KantorCabang;
import com.argonautb04.sirio.model.RencanaPemeriksaan;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.rest.Settings;
import com.argonautb04.sirio.rest.TugasPemeriksaanDTO;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.HasilPemeriksaanRestService;
import com.argonautb04.sirio.services.KantorCabangRestService;
import com.argonautb04.sirio.services.RencanaPemeriksaanRestService;
import com.argonautb04.sirio.services.RoleRestService;
import com.argonautb04.sirio.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
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
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan suatu rencana
     * pemeriksaan
     *
     * @param idRencanaPemeriksaan identifier rencana pemeriksaan
     * @return daftar tugas pemeriksaan yang terhubung dengan rencana pemeriksaan
     * tersebut
     */
    @GetMapping("/getByRencana/{idRencanaPemeriksaan}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukRencanaPemeriksaan(
            @PathVariable("idRencanaPemeriksaan") int idRencanaPemeriksaan) {
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
    private BaseResponse<List<TugasPemeriksaanDTO>> getAllTugasPemeriksaanUntukEmployee(Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        List<TugasPemeriksaan> daftarTugasPemeriksaanTemp = employee.getRole() == roleRestService.getById(5)
                ? tugasPemeriksaanRestService.getByPelaksana(employee)
                : tugasPemeriksaanRestService.getAll();

        List<TugasPemeriksaan> daftarTugasPemeriksaan = new ArrayList<>();
        for (TugasPemeriksaan tugasPemeriksaan : daftarTugasPemeriksaanTemp) {
            if (tugasPemeriksaan.getRencanaPemeriksaan().getStatus().getIdStatusRencana() == 2)
                daftarTugasPemeriksaan.add(tugasPemeriksaan);
        }

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
     * Mengambil seluruh tugas pemeriksaan yang terhubung dengan kantor cabang
     * tersebut
     *
     * @param idKantorCabang identifier kantor cabang
     * @return daftar tugas pemeriksaan yang terhubung dengan kantor cabang tersebut
     */
    @GetMapping("/getByKantorCabang/{idKantorCabang}")
    private BaseResponse<List<TugasPemeriksaan>> getAllTugasPemeriksaanUntukKantorCabang(
            @PathVariable("idKantorCabang") int idKantorCabang) {
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
            @PathVariable("idTugasPemeriksaan") int idTugasPemeriksaan) {
        return new BaseResponse<>(200, "success", tugasPemeriksaanRestService.validateExistById(idTugasPemeriksaan));
    }

    /**
     * Mengubah tugas pemeriksaan
     *
     * @param tugasPemeriksaanDTO data transfer object untuk tugas pemeriksaan yang
     *                            akan diubah
     * @return tugas pemeriksaan yang telah disimpan perubahannya
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<TugasPemeriksaan> ubahTugasPemeriksaan(@RequestBody TugasPemeriksaanDTO tugasPemeriksaanDTO) {
        TugasPemeriksaan tugasPemeriksaanTemp = tugasPemeriksaanRestService
                .validateExistById(tugasPemeriksaanDTO.getId());

        tugasPemeriksaanTemp.setRencanaPemeriksaan(tugasPemeriksaanTemp.getRencanaPemeriksaan());
        tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
        tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));

        LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
        LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());

        if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
            tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
            tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
        }

        TugasPemeriksaan tugasPemeriksaan = tugasPemeriksaanRestService
                .ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
        return new BaseResponse<>(200, "success", tugasPemeriksaan);
    }

    /**
     * Menghapus tugas pemeriksaan
     *
     * @param tugasPemeriksaanDTO data transfer object untuk tugas pemeriksaan yang
     *                            akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusTugasPemeriksaan(@RequestBody TugasPemeriksaanDTO tugasPemeriksaanDTO) {
        tugasPemeriksaanRestService.validateExistById(tugasPemeriksaanDTO.getId());
        tugasPemeriksaanRestService.hapusTugasPemeriksaan(tugasPemeriksaanDTO.getId());
        return new BaseResponse<>(200, "success",
                "Tugas pemeriksaan dengan id " + tugasPemeriksaanDTO.getId() + " terhapus!");
    }

}