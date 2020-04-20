package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.KomponenPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.RisikoRestService;
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
    private EmployeeRestService employeeRestService;

    /**
     * Menambahkan risiko baru.
     *
     * @param risikoDTO data transfer object untuk risiko yang akan ditambah
     * @return menyimpan risiko baru ke db
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<Risiko> tambahRisiko(
            @RequestBody RisikoDTO risikoDTO,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "tambah risiko");
        Risiko risikoTemp = risikoRestService.transformasidto(new Risiko(), risikoDTO);

        Risiko risiko = risikoRestService.buatRisiko(risikoTemp);

        return new BaseResponse<>(200, "success", risiko);
    }

    /**
     * Menampilkan satu risiko.
     *
     * @param idRisiko
     * @return object risiko beserta atribut yang sesuai dengan idRisiko tersebut
     */
    @GetMapping(value = "/{idRisiko}")
    private BaseResponse<Risiko> getRisiko(
            @PathVariable("idRisiko") int idRisiko,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses risiko");
        Risiko risiko = risikoRestService.validateExistById(idRisiko);
        return new BaseResponse<>(200, "success", risiko);
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
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "hapus risiko");
        Risiko risiko = risikoRestService.validateExistById(risikoDTO.getId());
        if (komponenPemeriksaanRestService.getByRisiko(risiko) != null) {
            risikoRestService.nonaktifkanRisiko(risiko.getIdRisiko());
            return new BaseResponse<>(403, "failed", "Risiko dengan id " + risiko.getIdRisiko() + " dinonaktifkan!");
        } else {
            risikoRestService.hapusRisiko(risiko.getIdRisiko());
        }
        return new BaseResponse<>(200, "success", "RIsiko dengan id " + risikoDTO.getId() + " terhapus!");
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
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "ubah risiko");

        Risiko risikoTemp = risikoRestService.validateExistById(risikoDTO.getId());
        risikoTemp = risikoRestService.transformasidto(risikoTemp, risikoDTO);

        Risiko result = risikoRestService.ubahRisiko(risikoTemp.getIdRisiko(), risikoTemp);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Menampilkan daftar risiko-risiko yang sudah ada.
     *
     * @return daftar risiko berupa list.
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RisikoDTO>> getAllRisiko(Principal principal) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "tabel risiko");

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

        return new BaseResponse<>(200, "success", daftarRisikoDTO);
    }

    /**
     * Mengambil daftar risiko di kategori 3
     *
     * @return daftar detail risiko di kategori 3
     */
    @GetMapping("/getAll/child")
    private BaseResponse<List<RisikoDTO>> getAllRisikoChild(Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "akses risiko");

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

        return new BaseResponse<>(200, "success", result);
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
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "tambah risiko");

        List<List<Risiko>> listOfOptionList = new ArrayList<>();
        listOfOptionList.add(
                risikoRestService.getByKategori(1)
        );
        listOfOptionList.add(
                risikoRestService.getByKategori(2)
        );

        return new BaseResponse<>(200, "success", listOfOptionList);
    }
}
