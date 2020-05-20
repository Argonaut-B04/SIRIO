package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.TemuanRisiko;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.DashboardDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    private BaseResponse<DashboardDTO> getAllData(@RequestParam("tanggalAwal") String dateAwal,
                                                  @RequestParam("tanggalAkhir") String dateAkhir,
                                                  Principal principal) throws ParseException {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses dashboard staff");
        BaseResponse<DashboardDTO> response = new BaseResponse<>();
        if (pengelola.getRole().getNamaRole().equals("QA Officer Operational Risk")
        || pengelola.getRole().getNamaRole().equals("QA Lead Operational Risk")
        || pengelola.getRole().getNamaRole().equals("Super QA Officer Operational Risk")) {
            response = getQAData(pengelola.getIdEmployee(), dateAwal, dateAkhir, principal);

        } else {
            DashboardDTO result = new DashboardDTO();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate tanggalAwal = null;
            LocalDate tanggalAkhir = null;
            if (!dateAwal.equals("")) {
                tanggalAwal = LocalDate.parse(dateAwal, formatter);
            }
            if (!dateAkhir.equals("")) {
                tanggalAkhir = LocalDate.parse(dateAkhir, formatter);
            }
            result.setListMonth(rekomendasiRestService.getListMonth(tanggalAwal, tanggalAkhir));
            Integer jumlahRekomendasi = null;
            Integer jumlahTemuan = null;

            if (tanggalAwal != null) {
                jumlahRekomendasi = rekomendasiRestService.getAll(tanggalAwal, tanggalAkhir).size();
                jumlahTemuan = temuanRisikoRestService.getAll(tanggalAwal, tanggalAkhir).size();
            } else {
                jumlahRekomendasi = rekomendasiRestService.getAllByStatus().size();
                jumlahTemuan = temuanRisikoRestService.getAll().size();
            }

            List<Integer> listTemuan = temuanRisikoRestService.getAllByMonth(tanggalAwal, tanggalAkhir);

            List<Rekomendasi> rekomendasiOverdue = rekomendasiRestService.getRekomendasiOverdue(tanggalAwal, tanggalAkhir);
            List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue, tanggalAwal, tanggalAkhir);
            Float jumlahRekomendasiOverdue = (float) rekomendasiOverdue.size() / (float) jumlahRekomendasi * (float) 100;

            List<Rekomendasi> rekomendasiDiimplementasi = rekomendasiRestService.getRekomendasiDiimplementasi(tanggalAwal, tanggalAkhir);
            List<Integer> listRekomendasiDiimplementasi = rekomendasiRestService.getRekomendasiByMonth(rekomendasiDiimplementasi, tanggalAwal, tanggalAkhir);
            Float jumlahRekomendasiDiimplementasi = (float) rekomendasiDiimplementasi.size() / (float) jumlahRekomendasi * (float) 100;

            List<Rekomendasi> rekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiBelumDiimplementasi(tanggalAwal, tanggalAkhir);
            List<Integer> listRekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiByMonth(rekomendasiBelumDiimplementasi, tanggalAwal, tanggalAkhir);
            Float jumlahRekomendasiBelumDiimplementasi = (float) rekomendasiBelumDiimplementasi.size() / (float) jumlahRekomendasi * (float) 100;

            result.setJumlahRekomendasiBelumDiimplementasi(jumlahRekomendasiBelumDiimplementasi);
            result.setListRekomendasiBelumDiimplementasi(listRekomendasiBelumDiimplementasi);
            result.setJumlahRekomendasiDiimplementasi(jumlahRekomendasiDiimplementasi);
            result.setListRekomendasiDiimplementasi(listRekomendasiDiimplementasi);
            result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);
            result.setListRekomendasiOverdue(listRekomendasiOverdue);
            result.setJumlahRekomendasi(jumlahRekomendasi);
            result.setJumlahTemuan(jumlahTemuan);
            result.setListTemuan(listTemuan);
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        }
        return response;
    }

    @GetMapping(value = "/qa")
    private BaseResponse<DashboardDTO> getQAData(@RequestParam("id") int idQa,
             @RequestParam("tanggalAwal") String dateAwal,
             @RequestParam("tanggalAkhir") String dateAkhir, Principal principal) throws ParseException {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses dashboard staff");
        BaseResponse<DashboardDTO> response = new BaseResponse<>();
        DashboardDTO result = new DashboardDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate tanggalAwal = null;
        LocalDate tanggalAkhir = null;
        if (!dateAwal.equals("")) {
            tanggalAwal = LocalDate.parse(dateAwal, formatter);
        }
        if (!dateAkhir.equals("")) {
            tanggalAkhir = LocalDate.parse(dateAkhir, formatter);
        }
        result.setListMonth(rekomendasiRestService.getListMonth(tanggalAwal, tanggalAkhir));

        Employee emp = null;
        String name;
        try {
            emp = employeeRestService.validateEmployeeExistById(idQa);
            name = emp.getNama();

        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "QA tidak ditemukan!"
            );
        }

        Integer jumlahRekomendasi = null;
        Integer jumlahTemuan = null;

        if (tanggalAwal != null) {
            jumlahRekomendasi = rekomendasiRestService.getByPembuat(idQa, tanggalAwal, tanggalAkhir).size();
            jumlahTemuan = temuanRisikoRestService.getByPembuat(idQa, tanggalAwal, tanggalAkhir).size();
        } else {
            jumlahRekomendasi = rekomendasiRestService.getByPembuat(idQa).size();
            jumlahTemuan = temuanRisikoRestService.getByPembuat(idQa).size();
        }

        List<Integer> listTemuan = temuanRisikoRestService.getByPembuatByMonth(idQa, tanggalAwal,tanggalAkhir);

        List<Rekomendasi> rekomendasiOverdue = rekomendasiRestService.getRekomendasiOverdueByPembuat(idQa, tanggalAwal, tanggalAkhir);
        List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue, tanggalAwal, tanggalAkhir);
        Float jumlahRekomendasiOverdue = (float)rekomendasiOverdue.size()/(float)jumlahRekomendasi*(float)100;


        List<Rekomendasi> rekomendasiDiimplementasi = rekomendasiRestService.getRekomendasiDiimplementasiByPembuat(idQa, tanggalAwal, tanggalAkhir);
        List<Integer> listRekomendasiDiimplementasi = rekomendasiRestService.getRekomendasiByMonth(rekomendasiDiimplementasi, tanggalAwal, tanggalAkhir);
        Float jumlahRekomendasiDiimplementasi = (float)rekomendasiDiimplementasi.size()/(float)jumlahRekomendasi*(float)100;

        List<Rekomendasi> rekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiBelumDiimplementasiByPembuat(idQa, tanggalAwal, tanggalAkhir);
        List<Integer> listRekomendasiBelumDiimplementasi = rekomendasiRestService.getRekomendasiByMonth(rekomendasiBelumDiimplementasi, tanggalAwal, tanggalAkhir);
        Float jumlahRekomendasiBelumDiimplementasi = (float)rekomendasiBelumDiimplementasi.size()/(float)jumlahRekomendasi*(float)100;

        result.setJumlahRekomendasiBelumDiimplementasi(jumlahRekomendasiBelumDiimplementasi);
        result.setListRekomendasiBelumDiimplementasi(listRekomendasiBelumDiimplementasi);
        result.setJumlahRekomendasiDiimplementasi(jumlahRekomendasiDiimplementasi);
        result.setListRekomendasiDiimplementasi(listRekomendasiDiimplementasi);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);
        result.setListRekomendasiOverdue(listRekomendasiOverdue);
        result.setJumlahRekomendasi(jumlahRekomendasi);
        result.setJumlahTemuan(jumlahTemuan);
        result.setListTemuan(listTemuan);
        result.setNamaqa(name);
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);

        return response;
    }
}
