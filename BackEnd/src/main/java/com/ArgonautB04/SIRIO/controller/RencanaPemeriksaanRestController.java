package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.*;
import com.ArgonautB04.SIRIO.services.*;

import com.ArgonautB04.SIRIO.services.RencanaPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
     * <p>
     * Changelog:
     * - Mengubah filter id pembuat dengan filter logged in user
     *
     * @return daftar rencana pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RencanaPemeriksaanDTO>> getAllRencanaPemeriksaan(Principal principal) {
        BaseResponse<List<RencanaPemeriksaanDTO>> response = new BaseResponse<>();

        try {
            Optional<Employee> employeeTarget = employeeRestService.getByUsername(principal.getName());
            Employee employee;
            if (employeeTarget.isPresent()) {
                employee = employeeTarget.get();
            } else {
                throw new NoSuchElementException();
            }
            List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getByPembuat(employee);
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
                for (TugasPemeriksaan tugasPemeriksaan: daftarTugasPemeriksaan){
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
     * Mengambil seluruh rencana pemeriksaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar rencana pemeriksaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<RencanaPemeriksaan>> getAllRencanaPemeriksaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        BaseResponse<List<RencanaPemeriksaan>> response = new BaseResponse<>();
        try {
            Employee pembuat = employeeRestService.getById(idPembuat);
            List<RencanaPemeriksaan> result = rencanaPemeriksaanRestService.getByPembuat(pembuat);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + idPembuat + " tidak ditemukan!"
            );
        }
        return response;
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
        BaseResponse<RencanaPemeriksaanDTO> response = new BaseResponse<>();
        try {
            RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.getById(idRencanaPemeriksaan);
            RencanaPemeriksaanDTO result = new RencanaPemeriksaanDTO();
            result.setId(rencanaPemeriksaan.getIdRencana());
            result.setStatus(rencanaPemeriksaan.getStatus().getIdStatusRencana());
            result.setLinkMajelis(rencanaPemeriksaan.getLinkMajelis());
            result.setNamaRencana(rencanaPemeriksaan.getNamaRencana());
            result.setDaftarTugasPemeriksaan(new ArrayList<>());
            for (TugasPemeriksaan tugasPemeriksaan:
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
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rencana Pemeriksaan dengan ID " + idRencanaPemeriksaan + " tidak ditemukan!"
            );
        }
        return response;
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
    ) {
        BaseResponse<RencanaPemeriksaan> response = new BaseResponse<>();
        RencanaPemeriksaan rencanaPemeriksaanTemp = new RencanaPemeriksaan();

        try {
            Employee employee = employeeRestService.getByUsername(principal.getName()).get();
            rencanaPemeriksaanTemp.setPembuat(employee);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + rencanaPemeriksaanDTO.getIdPembuat() + " tidak ditemukan!"
            );
        }

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

        if(rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan() != null &&
                !rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan().isEmpty()){
            for (TugasPemeriksaanDTO tugasPemeriksaanDTO: rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()){
                TugasPemeriksaan tugasPemeriksaanTemp = new TugasPemeriksaan();
                tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaan);

                if (tugasPemeriksaanDTO.getKantorCabang() != null) {
                    tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));;
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

                if (tugasPemeriksaanDTO.getTanggalMulai() != null && tugasPemeriksaanDTO.getTanggalSelesai() != null  ) {
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

                tugasPemeriksaanRestService.buatTugasPemeriksaan(tugasPemeriksaanTemp);
            }

        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(rencanaPemeriksaan);

        return response;
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
    ) {
        BaseResponse<RencanaPemeriksaan> response = new BaseResponse<>();

        RencanaPemeriksaan rencanaPemeriksaanTemp;
        try {
            rencanaPemeriksaanTemp = rencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getId());
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Renaca Pemeriksaan dengan ID " + rencanaPemeriksaanDTO.getId() + " tidak ditemukan!"
            );
        }

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
        RencanaPemeriksaan rencanaPemeriksaan = rencanaPemeriksaanRestService.ubahRencanaPemeriksaan(rencanaPemeriksaanDTO.getId(), rencanaPemeriksaanTemp);

        if(rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan() != null &&
                !rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan().isEmpty()) {
            for (TugasPemeriksaanDTO tugasPemeriksaanDTO : rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()) {
                TugasPemeriksaan tugasPemeriksaanTemp = new TugasPemeriksaan();
                tugasPemeriksaanTemp.setRencanaPemeriksaan(rencanaPemeriksaan);

                if (tugasPemeriksaanDTO.getKantorCabang() != null) {
                    tugasPemeriksaanTemp.setKantorCabang(kantorCabangRestService.getById(tugasPemeriksaanDTO.getKantorCabang()));
                    ;
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
                tugasPemeriksaanRestService.ubahTugasPemeriksaan(tugasPemeriksaanDTO.getId(), tugasPemeriksaanTemp);
            }
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(rencanaPemeriksaan);

        return response;
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
        RencanaPemeriksaan rencanaPemeriksaan;
        try {
            rencanaPemeriksaan = rencanaPemeriksaanRestService.getById(rencanaPemeriksaanDTO.getId());

        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rencana pemeriksaan dengan ID " + rencanaPemeriksaanDTO.getId() + " tidak ditemukan!"
            );
        }

        //Tidak dapat dihapus jika rencana sudah dijalankan
        if(rencanaPemeriksaanDTO.getStatus() > 1){
            response.setStatus(500);
            response.setMessage("error");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " tidak dapat dihapus!");
        }else {
            for (TugasPemeriksaanDTO tugasPemeriksaanDTO : rencanaPemeriksaanDTO.getDaftarTugasPemeriksaan()) {
                TugasPemeriksaan tugasPemeriksaanTemp = tugasPemeriksaanRestService.getById(tugasPemeriksaanDTO.getId());
                tugasPemeriksaanRestService.hapusTugasPemeriksaan(tugasPemeriksaanTemp.getIdTugas());
            }
            rencanaPemeriksaanRestService.hapusRencanaPemeriksaan(rencanaPemeriksaan.getIdRencana());
            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Rencana pemeriksaan dengan id " + rencanaPemeriksaanDTO.getId() + " terhapus!");
        }

        return response;
    }
}



