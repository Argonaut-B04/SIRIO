//package com.ArgonautB04.SIRIO.controller;
//
//import com.ArgonautB04.SIRIO.model.Employee;
//import com.ArgonautB04.SIRIO.model.Rekomendasi;
//import com.ArgonautB04.SIRIO.rest.BaseResponse;
//import com.ArgonautB04.SIRIO.rest.DashboardDTO;
//import com.ArgonautB04.SIRIO.services.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/DashboardStaff")
//public class DashboardStaffController {
//
//    @Autowired
//    private HasilPemeriksaanRestService hasilPemeriksaanRestService;
//
//    @Autowired
//    private EmployeeRestService employeeRestService;
//
//    @Autowired
//    private TugasPemeriksaanRestService tugasPemeriksaanRestService;
//
//    @Autowired
//    private StatusHasilPemeriksaanRestService statusHasilPemeriksaanRestService;
//
//    @Autowired
//    private RiskLevelRestService riskLevelRestService;
//
//    @Autowired
//    private RisikoRestService risikoRestService;
//
//    @Autowired
//    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;
//
//    @Autowired
//    private TemuanRisikoRestService temuanRisikoRestService;
//
//    @Autowired
//    private RekomendasiRestService rekomendasiRestService;
//
//    @Autowired
//    private StatusRekomendasiRestService statusRekomendasiRestService;
//
//    @Autowired
//    private RoleRestService roleRestService;
//
//    @GetMapping("/getAllData")
//    private BaseResponse<DashboardDTO> getAllData(Principal principal) {
//        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
//        employeeRestService.validateRolePermission(pengelola, "akses dashboard staff");
//        BaseResponse<DashboardDTO> response = new BaseResponse<>();
//        DashboardDTO result = new DashboardDTO();
//        result.setListMonth(rekomendasiRestService.getListMonth());
//
//        Integer jumlahRekomendasi = rekomendasiRestService.getAll().size();
//        Integer jumlahTemuan = temuanRisikoRestService.getAll().size();
//        List<Integer> listTemuan = temuanRisikoRestService.getAllByMonth();
//
//        List<Rekomendasi> rekomendasiOverdue = rekomendasiRestService.getRekomendasiOverdue();
//        List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue);
//        Float jumlahRekomendasiOverdue = (float)rekomendasiRestService.getRekomendasiOverdue().size()/(float)jumlahRekomendasi*(float)100;
//
//        List<Rekomendasi> rekomendasiDiimplementasi = rekomendasiRestService.getRekomendasiDiimplementasi();
//        List<Integer> listRekomendasiDiimplementasi = rekomendasiRestService.getRekomendasiByMonth(rekomendasiDiimplementasi);
//        Float jumlahRekomendasiDiimplementasi = (float)rekomendasiRestService.getRekomendasiDiimplementasi().size()/(float)jumlahRekomendasi*(float)100;
//
//        List<Rekomendasi> rekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiBelumDiimplementasi();
//        List<Integer> listRekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiByMonth(rekomendasiBelumDiimplementasi);
//        Float jumlahRekomendasiBelumDiimplementasi = (float)rekomendasiRestService.getRekomendasiBelumDiimplementasi().size()/(float)jumlahRekomendasi*(float)100;
//
//        result.setJumlahRekomendasiBelumDiimplementasi(jumlahRekomendasiBelumDiimplementasi);
//        result.setListRekomendasiBelumDiimplementasi(listRekomendasiBelumDiimplementasi);
//        result.setJumlahRekomendasiDiimplementasi(jumlahRekomendasiDiimplementasi);
//        result.setListRekomendasiDiimplementasi(listRekomendasiDiimplementasi);
//        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);
//        result.setListRekomendasiOverdue(listRekomendasiOverdue);
//        result.setJumlahRekomendasi(jumlahRekomendasi);
//        result.setJumlahTemuan(jumlahTemuan);
//        result.setListTemuan(listTemuan);
//        response.setStatus(200);
//        response.setMessage("success");
//        response.setResult(result);
//        return response;
//    }
//
//}
