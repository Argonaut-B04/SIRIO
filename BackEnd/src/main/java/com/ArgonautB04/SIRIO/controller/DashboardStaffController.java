package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.DashboardDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/DashboardStaff")
public class DashboardStaffController {

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private StatusHasilPemeriksaanRestService statusHasilPemeriksaanRestService;

    @Autowired
    private RiskLevelRestService riskLevelRestService;

    @Autowired
    private RisikoRestService risikoRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private TemuanRisikoRestService temuanRisikoRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private StatusRekomendasiRestService statusRekomendasiRestService;

    @Autowired
    private RoleRestService roleRestService;

    @GetMapping("/getAllData")
    private BaseResponse<DashboardDTO> getAllData(Principal principal) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses dashboard staff");
        BaseResponse<DashboardDTO> response = new BaseResponse<>();
        DashboardDTO result = new DashboardDTO();
        Integer jumlahRekomendasi = rekomendasiRestService.getAll().size();
        Integer jumlahTemuan = temuanRisikoRestService.getAll().size();
        Integer jumlahRekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiBelumDiimplementasi().size();
        Integer jumlahRekomendasiOverdue = rekomendasiRestService.getRekomendasiOverdue().size();
        Integer jumlahRekomendasiDiimplementasi = jumlahRekomendasi - jumlahRekomendasiBelumDiimplementasi - jumlahRekomendasiOverdue;
        result.setJumlahRekomendasiBelumDiimplementasi(jumlahRekomendasiBelumDiimplementasi);
        result.setJumlahRekomendasiDiimplementasi(jumlahRekomendasiDiimplementasi);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);
        result.setJumlahRekomendasi(jumlahRekomendasi);
        result.setJumlahTemuan(jumlahTemuan);
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

}
