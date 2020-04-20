package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.KomponenPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.RisikoRestService;
import com.ArgonautB04.SIRIO.services.SOPRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Risiko")
public class RisikoRestController {

    @Autowired
    private RisikoRestService risikoRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private SOPRestService sopRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Menambahkan risiko baru.
     *
     * @param risikoDTO data transfer object untuk risiko yang akan ditambah
     * @return menyimpan risiko baru ke db
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<Risiko> tambahRisiko(
            @RequestBody RisikoDTO risikoDTO, Principal principal
    ) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesTambahRisiko()) {
                Risiko risikoTemp = new Risiko();
                risikoTemp = risikoRestService.transformasidto(risikoTemp, risikoDTO);

                Risiko risiko = risikoRestService.buatRisiko(risikoTemp);
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(risiko);

                return response;
            } else throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
                );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Menampilkan satu risiko.
     *
     * @param idRisiko
     * @return object risiko beserta atribut yang sesuai dengan idRisiko tersebut
     */
    @GetMapping(value = "/{idRisiko}")
    private BaseResponse<Risiko> getRisiko(@PathVariable("idRisiko") int idRisiko, Principal principal) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesRisiko()) {
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
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Menghapus satu risiko.
     *
     * @param risikoDTO data transfer object untuk risiko yang akan dihapus
     * @return risiko dengan idRisiko tersebut dihapus dari db
     */
    @PostMapping(value = "/hapus")
    private BaseResponse<String> hapusRisiko(@RequestBody RisikoDTO risikoDTO, Principal principal
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesHapusRisiko()) {
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
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Mengubah risiko, semua atribut dapat diubah.
     *
     * @param risikoDTO data transfer object untuk risiko yang akan diubah
     * @return perubahan data akan disimpan di db
     */
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<Risiko> ubahRisiko(
            @RequestBody RisikoDTO risikoDTO, Principal principal
    ) {
        BaseResponse<Risiko> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesUbahRisiko()) {
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

            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Menampilkan daftar risiko-risiko yang sudah ada.
     *
     * @return daftar risiko berupa list.
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RisikoDTO>> getAllRisiko(Principal principal) {
        BaseResponse<List<RisikoDTO>> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesTabelRisiko()) {
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
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Mengambil daftar risiko di kategori 3
     *
     * @return daftar detail risiko di kategori 3
     */
    @GetMapping("/getAll/child")
    private BaseResponse<List<RisikoDTO>> getAllRisikoChild(Principal principal) {
        BaseResponse<List<RisikoDTO>> response = new BaseResponse<>();
        List<RisikoDTO> result = new ArrayList<>();

        List<Risiko> daftarRisiko = risikoRestService.getByKategori(3);
        for (Risiko risiko : daftarRisiko) {
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
    private BaseResponse<List<RisikoDTO>> ubahHierarkiRisiko(
            @RequestBody List<RisikoDTO> risikoList, Principal principal
    ) {
        BaseResponse<List<RisikoDTO>> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesUbahHierarki()) {
                try {
                    List<RisikoDTO> listRisikoBaru = new ArrayList<>();
                    for (RisikoDTO risk : risikoList) {
                        Risiko risiko = risikoRestService.getById(risk.getId());
                        if (risk.getParent() != null) {
                            if (risiko.getParent() == null || risk.getParent() != risiko.getParent().getIdRisiko()) {
                                risiko = risikoRestService.transformasidto(risiko, risk);
                                risk = risikoRestService.ubahHierarki(risiko, risk);
                                risk.setId(risiko.getIdRisiko());
                                risk.setSop(risiko.getSop().getIdSop());
                                listRisikoBaru.add(risk);
                            } else {
                                listRisikoBaru.add(risk);
                            }
                        } else {
                            listRisikoBaru.add(risk);
                        }
                        response.setStatus(200);
                        response.setMessage("success");
                        response.setResult(listRisikoBaru);
                    }
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Ubah hierarki risiko gagal"
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

    @GetMapping("/ubah-hierarki/kategori")
    private BaseResponse<List<List<Risiko>>> getByKategori(Principal principal) {
        BaseResponse<List<List<Risiko>>> response = new BaseResponse<>();
//        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
//        Employee pengelola;
//        if (pengelolaOptional.isPresent()) {
//            pengelola = pengelolaOptional.get();
//            if (pengelola.getRole().getAccessPermissions().getAksesTambahRisiko()) {
                List<List<Risiko>> listOfOptionList = new ArrayList<>();
                listOfOptionList.add(
                        risikoRestService.getByKategori(1)
                );
                listOfOptionList.add(
                        risikoRestService.getByKategori(2)
                );
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(listOfOptionList);
                return response;
            }
        }
//        else throw new ResponseStatusException(
//                HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
//                );
//                } else throw new ResponseStatusException(
//                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
//                );
//                }
