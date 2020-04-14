package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.StatusBuktiPelaksanaan;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.BuktiPelaksanaanDTO;
import com.ArgonautB04.SIRIO.services.BuktiPelaksanaanRestService;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.StatusBuktiPelaksanaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/BuktiPelaksanaan")
public class BuktiPelaksanaanRestController {

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private StatusBuktiPelaksanaanRestService statusBuktiPelaksanaanRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    /**
     * Mengambil seluruh bukti pelaksanaan
     *
     * @return daftar bukti pelaksanaan
     */
    @GetMapping("/getAll")
    private BaseResponse<List<BuktiPelaksanaan>> getAllBuktiPelaksanaan() {
        BaseResponse<List<BuktiPelaksanaan>> response = new BaseResponse<>();
        List<BuktiPelaksanaan> result = buktiPelaksanaanRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh bukti pelaksanaan yang terhubung dengan pembuat
     *
     * @param idPembuat identifier pembuat
     * @return daftar bukti pelaksanaan yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll/{idPembuat}")
    private BaseResponse<List<BuktiPelaksanaan>> getAllBuktiPelaksanaanUntukPembuat(
            @PathVariable("idPembuat") int idPembuat
    ) {
        BaseResponse<List<BuktiPelaksanaan>> response = new BaseResponse<>();
        try {
            Employee pembuat = employeeRestService.getById(idPembuat);
            List<BuktiPelaksanaan> result = buktiPelaksanaanRestService.getByPembuat(pembuat);

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
     * Mengambil suatu bukti pelaksanaan
     *
     * @param idBuktiPelaksanaan identifier bukti pelaksanaan
     * @return detail bukti pelaksanaan
     */
    @GetMapping("/{idBuktiPelaksanaan}")
    private BaseResponse<BuktiPelaksanaanDTO> getDetailBuktiPelaksanaan(
            @PathVariable("idBuktiPelaksanaan") int idBuktiPelaksanaan, Principal principal
    ) {
        BaseResponse<BuktiPelaksanaanDTO> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesBuktiPelaksanaan()) {
                try {
                    BuktiPelaksanaan buktiPelaksanaan = buktiPelaksanaanRestService.getById(idBuktiPelaksanaan);
                    BuktiPelaksanaanDTO result = new BuktiPelaksanaanDTO();
                    result.setId(buktiPelaksanaan.getIdBuktiPelaksanaan());
                    result.setStatus(buktiPelaksanaan.getStatusBuktiPelaksanaan().getIdStatusBukti());
                    result.setKeterangan(buktiPelaksanaan.getKeterangan());
                    result.setLampiran(buktiPelaksanaan.getLampiran());
                    result.setFeedback(buktiPelaksanaan.getFeedback());
                    result.setNamaPembuat(buktiPelaksanaan.getPembuat().getNama());
                    result.setIdRekomendasi(buktiPelaksanaan.getRekomendasi().getIdRekomendasi());

                    response.setStatus(200);
                    response.setMessage("success");
                    response.setResult(result);
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Bukti pelaksanaan dengan ID " + idBuktiPelaksanaan + " tidak ditemukan!"
                    );
                }
                return response;
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Menambah bukti pelaksanaan baru untuk rekomendasi spesifik
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan yang akan ditambah
     * @return bukti pelaksanaan yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<BuktiPelaksanaan> tambahBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO,
            Principal principal, ModelMap model
    ) {
        BaseResponse<BuktiPelaksanaan> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesTambahBuktiPelaksanaan()) {
                BuktiPelaksanaan buktiPelaksanaanTemp = new BuktiPelaksanaan();
                buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(
                        statusBuktiPelaksanaanRestService.getById(1));

                if (buktiPelaksanaanDTO.getKeterangan() != null && !buktiPelaksanaanDTO.getKeterangan().equals("")) {
                    buktiPelaksanaanTemp.setKeterangan(buktiPelaksanaanDTO.getKeterangan());
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Keterangan bukti pelaksanaan perlu diisi!"
                    );
                }
                if (buktiPelaksanaanDTO.getLampiran() != null && !buktiPelaksanaanDTO.getLampiran().equals("")) {
                    if (buktiPelaksanaanDTO.getLampiran().contains("https://") |
                            buktiPelaksanaanDTO.getLampiran().contains("http://")) {
                        buktiPelaksanaanTemp.setLampiran(buktiPelaksanaanDTO.getLampiran());
                    } else {
                        throw new ResponseStatusException(
                                HttpStatus.FORBIDDEN, "Lampiran bukti pelaksanaan harus berupa link url!"
                        );
                    }
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Lampiran bukti pelaksanaan perlu diisi!"
                    );
                }

                buktiPelaksanaanTemp.setPembuat(pengelola);

                try {
                    Rekomendasi rekomendasi = rekomendasiRestService.getById(buktiPelaksanaanDTO.getIdRekomendasi());
                    buktiPelaksanaanTemp.setRekomendasi(rekomendasi);
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + buktiPelaksanaanDTO.getIdRekomendasi() + " tidak ditemukan!"
                    );
                }

                BuktiPelaksanaan buktiPelaksanaan = buktiPelaksanaanRestService.buatBuktiPelaksanaan(buktiPelaksanaanTemp);
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(buktiPelaksanaan);
                return response;
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Mengubah bukti pelaksanaan
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan yang akan diubah
     * @return bukti pelaksanaan yang telah disimpan
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<BuktiPelaksanaan> ubahBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO, Principal principal
    ) {
        BaseResponse<BuktiPelaksanaan> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesUbahBuktiPelaksanaan()) {
                try {
                    BuktiPelaksanaan buktiPelaksanaanTemp = buktiPelaksanaanRestService.getById(buktiPelaksanaanDTO.getId());
                    buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(
                            statusBuktiPelaksanaanRestService.getById(1));

                    if (buktiPelaksanaanDTO.getKeterangan() != null && !buktiPelaksanaanDTO.getKeterangan().equals("")) {
                        buktiPelaksanaanTemp.setKeterangan(buktiPelaksanaanDTO.getKeterangan());
                    } else {
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Keterangan bukti pelaksanaan perlu diisi!"
                        );
                    }

                    if (buktiPelaksanaanDTO.getLampiran() != null && !buktiPelaksanaanDTO.getLampiran().equals("")) {
                        if (buktiPelaksanaanDTO.getLampiran().contains("https://") |
                                buktiPelaksanaanDTO.getLampiran().contains("http://")) {
                            buktiPelaksanaanTemp.setLampiran(buktiPelaksanaanDTO.getLampiran());
                        } else {
                            throw new ResponseStatusException(
                                    HttpStatus.FORBIDDEN, "Lampiran bukti pelaksanaan harus berupa link url!"
                            );
                        }
                    } else {
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Lampiran bukti pelaksanaan perlu diisi!"
                        );
                    }

                    BuktiPelaksanaan buktiPelaksanaan =
                            buktiPelaksanaanRestService.ubahBuktiPelaksanaan(buktiPelaksanaanDTO.getId(), buktiPelaksanaanTemp);

                    response.setStatus(200);
                    response.setMessage("success");
                    response.setResult(buktiPelaksanaan);
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Bukti Pelaksanaan dengan ID " + buktiPelaksanaanDTO.getId() + " tidak ditemukan!"
                    );
                }
                return response;
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Menyetujui atau menolak bukti pelaksanaan
     *
     * @param buktiPelaksanaanDTO data transfer object untuk bukti pelaksanaan
     */
    @PostMapping(value = "/persetujuan", consumes = {"application/json"})
    private BaseResponse<String> persetujuanBuktiPelaksanaan(
            @RequestBody BuktiPelaksanaanDTO buktiPelaksanaanDTO,
            Principal principal, ModelMap model
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesPersetujuanBuktiPelaksanaan()) {
                try {
                    BuktiPelaksanaan buktiPelaksanaanTemp = buktiPelaksanaanRestService.getById(buktiPelaksanaanDTO.getId());
                    buktiPelaksanaanTemp.setPemeriksa(pengelola);

                    try {
                        StatusBuktiPelaksanaan statusBuktiPelaksanaan = statusBuktiPelaksanaanRestService.getById(
                                buktiPelaksanaanDTO.getStatus());
                        if (buktiPelaksanaanTemp.getStatusBuktiPelaksanaan().getIdStatusBukti() != 1 ||
                                (statusBuktiPelaksanaan != statusBuktiPelaksanaanRestService.getById(2) &&
                                        statusBuktiPelaksanaan != statusBuktiPelaksanaanRestService.getById(3)))
                            throw new ResponseStatusException(
                                    HttpStatus.FORBIDDEN, "Pengajuan persetujuan tidak diperbolehkan!"
                            );
                        buktiPelaksanaanTemp.setStatusBuktiPelaksanaan(statusBuktiPelaksanaan);
                    } catch (NoSuchElementException e) {
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Status bukti pelaksanaan tidak ditemukan!"
                        );
                    }

                    if (buktiPelaksanaanDTO.getStatus() == 3 && (buktiPelaksanaanDTO.getFeedback() == null ||
                            buktiPelaksanaanDTO.getFeedback().equals("")))
                        throw new ResponseStatusException(
                                HttpStatus.FORBIDDEN, "Feedback perlu diisi untuk penolakan bukti pelaksanaan!"
                        );
                    buktiPelaksanaanTemp.setFeedback(buktiPelaksanaanDTO.getFeedback());

                    buktiPelaksanaanRestService.ubahBuktiPelaksanaan(buktiPelaksanaanDTO.getId(), buktiPelaksanaanTemp);

                    if (buktiPelaksanaanDTO.getStatus() == 3) {
                        response.setResult("Bukti pelaksanaan dengan id " + buktiPelaksanaanDTO.getId() + " ditolak!");
                    } else {
                        response.setResult("Bukti pelaksanaan dengan id " + buktiPelaksanaanDTO.getId() + " disetujui!");
                    }
                    response.setStatus(200);
                    response.setMessage("success");
                } catch (EmptyResultDataAccessException e) {
                    response.setStatus(404);
                    response.setMessage("not found");
                    response.setResult("Bukti pelaksanaan dengan id " +
                            buktiPelaksanaanDTO.getId() + " tidak dapat ditemukan");
                }
                return response;
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }
}
