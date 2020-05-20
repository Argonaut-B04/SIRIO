package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RencanaPemeriksaanDTO;
import com.ArgonautB04.SIRIO.rest.Settings;
import com.ArgonautB04.SIRIO.rest.TugasPemeriksaanDTO;
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
@RequestMapping("/api/v1/RencanaPemeriksaan")
public class RencanaPemeriksaanRestController {

    @Autowired
    private RencanaPemeriksaanRestService rencanaPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private StatusRencanaPemeriksaanRestService statusRencanaPemeriksaanRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;


    /**
     * Mengambil seluruh rencana pemeriksaan yang terhubung dengan user yang sedang login
     *
     * @return daftar rencana pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RencanaPemeriksaanDTO>> getAllRencanaPemeriksaan(Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        //TODO: add access permission untuk rencana pemeriksaan
        List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getAll();
        List<RencanaPemeriksaanDTO> resultDTO = new ArrayList<>();
        for (RencanaPemeriksaan rencanaPemeriksaan : result) {
            RencanaPemeriksaanDTO rencanaPemeriksaanDTO = new RencanaPemeriksaanDTO();
            rencanaPemeriksaanDTO.setId(rencanaPemeriksaan.getIdRencana());
            rencanaPemeriksaanDTO.setStatus(rencanaPemeriksaan.getStatus().getIdStatusRencana());
            rencanaPemeriksaanDTO.setNamaRencana(rencanaPemeriksaan.getNamaRencana());
            rencanaPemeriksaanDTO.setLinkMajelis(rencanaPemeriksaan.getLinkMajelis());
            rencanaPemeriksaanDTO.setIdPembuat(rencanaPemeriksaan.getPembuat().getIdEmployee());

            List<TugasPemeriksaan> daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByRencana(rencanaPemeriksaan);
            List<TugasPemeriksaanDTO> daftarTugasDTO = new ArrayList<>();
            for (TugasPemeriksaan tugasPemeriksaan : daftarTugasPemeriksaan) {
                TugasPemeriksaanDTO tugasPemeriksaanDTO = new TugasPemeriksaanDTO();
                tugasPemeriksaanDTO.setId(tugasPemeriksaan.getIdTugas());
                tugasPemeriksaanDTO.setKantorCabang(tugasPemeriksaan.getKantorCabang().getIdKantor());
                tugasPemeriksaanDTO.setIdQA(tugasPemeriksaan.getPelaksana().getIdEmployee());
                tugasPemeriksaanDTO.setTanggalSelesai(tugasPemeriksaan.getTanggalSelesai().toString());
                tugasPemeriksaanDTO.setTanggalMulai(tugasPemeriksaan.getTanggalMulai().toString());
                daftarTugasDTO.add(tugasPemeriksaanDTO);
            }
            rencanaPemeriksaanDTO.setDaftarTugasPemeriksaan(daftarTugasDTO);
            resultDTO.add(rencanaPemeriksaanDTO);
        }

        return new BaseResponse<>(200, "success", resultDTO);
    }

    @GetMapping("/check/{namaRencana}")
    private BaseResponse<Boolean> isExistInDatabase(
            @PathVariable("namaRencana") String namaRencana
    ) {
        Optional<RencanaPemeriksaan> rencanaPemeriksaan = rencanaPemeriksaanRestService.getByNama(namaRencana);
        return new BaseResponse<>(200, "success", rencanaPemeriksaan.isPresent());
    }

    /**
     * Mengambil seluruh rencana pemeriksaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar rencana pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<RencanaPemeriksaan>> getAllRencanaPemeriksaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        Employee pembuat = employeeRestService.validateEmployeeExistById(idPembuat);
        List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getByPembuat(pembuat);

        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Mengambil suatu rencana pemeriksaan
     *
     * @param idRencanaPemeriksaan identifier rencana pemeriksaan
     * @return detail rencana pemeriksaan
     */
    @GetMapping("/{idRencanaPemeriksaan}")
    private BaseResponse<RencanaPemeriksaanDTO> getRencanaPemeriksaan(
            @PathVariable("idRencanaPemeriksaan") int idRencanaPemeriksaan
    ) {
        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.validateExistById(idRencanaPemeriksaan);

        RencanaPemeriksaanDTO result = new RencanaPemeriksaanDTO();
        result.setId(rencanaPemeriksaan.getIdRencana());
        result.setStatus(rencanaPemeriksaan.getStatus().getIdStatusRencana());
        result.setLinkMajelis(rencanaPemeriksaan.getLinkMajelis());
        result.setNamaRencana(rencanaPemeriksaan.getNamaRencana());
        result.setDaftarTugasPemeriksaan(new ArrayList<>());
        for (TugasPemeriksaan tugasPemeriksaan :
                tugasPemeriksaanRestService.getByRencana(rencanaPemeriksaan)) {
            TugasPemeriksaanDTO tugasPemeriksaanDTO = new TugasPemeriksaanDTO();
            tugasPemeriksaanDTO.setId(tugasPemeriksaan.getIdTugas());
            tugasPemeriksaanDTO.setTanggalSelesai(tugasPemeriksaan.getTanggalSelesai().toString());
            tugasPemeriksaanDTO.setTanggalMulai(tugasPemeriksaan.getTanggalMulai().toString());
            tugasPemeriksaanDTO.setNamaQA(tugasPemeriksaan.getPelaksana().getNama());
            tugasPemeriksaanDTO.setIdQA(tugasPemeriksaan.getPelaksana().getIdEmployee());
            tugasPemeriksaanDTO.setKantorCabang(tugasPemeriksaan.getKantorCabang().getIdKantor());
            tugasPemeriksaanDTO.setNamaKantorCabang(tugasPemeriksaan.getKantorCabang().getNamaKantor());
            result.getDaftarTugasPemeriksaan().add(tugasPemeriksaanDTO);
        }

        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Menambah rencana pemeriksaan baru
     *
     * @param rencanaPemeriksaanDTO data transfer object untuk rencana pemeriksaan yang akan ditambah
     * @return rencana pemeriksaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<RencanaPemeriksaan> tambahRencanaPemeriksaan(
            @RequestBody RencanaPemeriksaanDTO rencanaPemeriksaanDTO,
            Principal principal
    ) throws ParseException {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        RencanaPemeriksaan rencanaPemeriksaanTemp = new RencanaPemeriksaan();
        rencanaPemeriksaanTemp.setPembuat(employee);

        if (rencanaPemeriksaanDTO.getNamaRencana() != null && !rencanaPemeriksaanDTO.getNamaRencana().equals("")) {
            rencanaPemeriksaanTemp.setNamaRencana(rencanaPemeriksaanDTO.getNamaRencana());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nama Rencana belum terisi!"
            );
        }

        if (rencanaPemeriksaanDTO.getLinkMajelis() != null && !rencanaPemeriksaanDTO.getLinkMajelis().equals("")) {
            rencanaPemeriksaanTemp.setLinkMajelis(rencanaPemeriksaanDTO.getLinkMajelis());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link Pemeriksaan belum terisi!"
            );
        }

        rencanaPemeriksaanTemp.setStatus(statusRencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getStatus()));
        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.buatRencanaPemeriksaan(rencanaPemeriksaanTemp);

        if (rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan() != null &&
                !rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan().isEmpty()) {
            for (TugasPemeriksaanDTO tugasPemeriksaanDTO : rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()) {
                TugasPemeriksaan tugasPemeriksaanTemp = new TugasPemeriksaan();
                tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaan);

                if (tugasPemeriksaanDTO.getKantorCabang() != null) {
                    tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Kantor Cabang belum terisi!"
                    );
                }

                if (tugasPemeriksaanDTO.getIdQA() != null) {
                    tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Pelaksana belum terisi!"
                    );
                }

                if (tugasPemeriksaanDTO.getTanggalMulai() != null && tugasPemeriksaanDTO.getTanggalSelesai() != null) {
                    LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
                    LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());

                    if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
                        tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
                        tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
                    } else {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, "Tanggal mulai harus lebih kecil daripada tanggal selesai!"
                        );
                    }
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Tanggal belum terisi!"
                    );
                }

                tugasPemeriksaanRestService.buatTugasPemeriksaan(tugasPemeriksaanTemp);
            }

        }

        return new BaseResponse<>(200, "success", rencanaPemeriksaan);
    }

    /**
     * Mengubah rencana pemeriksaan
     *
     * @param rencanaPemeriksaanDTO data transfer object untuk rencana pemeriksaan yang akan diubah
     * @return rencana pemeriksaan yang telah disimpan perubahannya
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<RencanaPemeriksaan> ubahRencanaPemeriksaan(
            @RequestBody RencanaPemeriksaanDTO rencanaPemeriksaanDTO
    ) throws ParseException {
        BaseResponse<RencanaPemeriksaan> response = new BaseResponse<>();

        RencanaPemeriksaan rencanaPemeriksaanTemp = rencanaPemeriksaanRestService.validateExistById(rencanaPemeriksaanDTO.getId());

        if (rencanaPemeriksaanDTO.getNamaRencana() != null && !rencanaPemeriksaanDTO.getNamaRencana().equals("")) {
            rencanaPemeriksaanTemp.setNamaRencana(rencanaPemeriksaanDTO.getNamaRencana());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nama Rencana belum terisi!"
            );
        }

        if (rencanaPemeriksaanDTO.getLinkMajelis() != null && !rencanaPemeriksaanDTO.getLinkMajelis().equals("")) {
            rencanaPemeriksaanTemp.setLinkMajelis(rencanaPemeriksaanDTO.getLinkMajelis());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link Pemeriksaan belum terisi!"
            );
        }

        rencanaPemeriksaanTemp.setStatus(statusRencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getStatus()));

        List<TugasPemeriksaan> tugasLama = tugasPemeriksaanRestService.getByRencana(rencanaPemeriksaanTemp);
        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.ubahRencanaPemeriksaan(rencanaPemeriksaanDTO.getId(), rencanaPemeriksaanTemp);
        List<TugasPemeriksaan> tugasBaru = new ArrayList<>();

        if (rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan() != null &&
                !rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan().isEmpty()) {

            for (TugasPemeriksaanDTO tugasPemeriksaanDTO : rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()) {
                TugasPemeriksaan tugasPemeriksaanTemp = new TugasPemeriksaan();
                tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaanTemp);

                if (tugasPemeriksaanDTO.getKantorCabang() != null) {
                    tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Kantor Cabang belum terisi!"
                    );
                }

                if (tugasPemeriksaanDTO.getIdQA() != null) {
                    tugasPemeriksaanTemp.setPelaksana(employeeRestService.getById(tugasPemeriksaanDTO.getIdQA()));
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Pelaksana belum terisi!"
                    );
                }

                if (tugasPemeriksaanDTO.getTanggalMulai() != null && tugasPemeriksaanDTO.getTanggalSelesai() != null) {
                    LocalDate tanggalMulaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalMulai());
                    LocalDate tanggalSelesaiLocalDate = Settings.stringToLocalDate(tugasPemeriksaanDTO.getTanggalSelesai());

                    if (tanggalMulaiLocalDate.compareTo(tanggalSelesaiLocalDate) < 0) {
                        tugasPemeriksaanTemp.setTanggalMulai(tanggalMulaiLocalDate);
                        tugasPemeriksaanTemp.setTanggalSelesai(tanggalSelesaiLocalDate);
                    }
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Tanggal belum terisi!"
                    );
                }
                //TugasPemeriksaan tugasPemeriksaan1 = tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
                //tugasBaru.add(tugasPemeriksaan1);

                if (tugasPemeriksaanRestService.isExistInDatabase(tugasPemeriksaanTemp)) {
                    tugasBaru.add(tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp));
                } else {
                    tugasBaru.add(
                            tugasPemeriksaanRestService.buatTugasPemeriksaan(tugasPemeriksaanTemp)
                    );
                }

            }
            tugasLama.removeAll(tugasBaru);
            for (TugasPemeriksaan tugas : tugasLama) {
                tugasPemeriksaanRestService.hapusTugasPemeriksaan(tugas.getIdTugas());
            }

        }

        return new BaseResponse<>(200, "success", rencanaPemeriksaan);
    }

    /**
     * Menghapus rencana pemeriksaan
     *
     * @param rencanaPemeriksaanDTO data transfer object untuk rencana pemeriksaan yang akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusRencanaPemeriksaan(
            @RequestBody RencanaPemeriksaanDTO rencanaPemeriksaanDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.validateExistById(rencanaPemeriksaanDTO.getId());

        //Tidak dapat dihapus jika rencana sudah dijalankan
        if (rencanaPemeriksaan.getStatus().getIdStatusRencana() > 1) {
            response.setStatus(500);
            response.setMessage("error");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " tidak dapat dihapus!");
        } else {
            rencanaPemeriksaanRestService.hapusRencanaPemeriksaan(rencanaPemeriksaan.getIdRencana());
            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " terhapus!");
        }

        return response;
    }
}



