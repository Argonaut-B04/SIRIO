package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.DashboardDTO;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.services.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Dashboard")
public class DashboardController {

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    @Autowired
    private TemuanRisikoRestService temuanRisikoRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    /**
     * Mengambil seluruh komponen dashboard
     *
     * @return komponen dashboard (rekomendasi, temuan risiko, pemeriksaan)
     */
//    @GetMapping("/getAll")
//    private BaseResponse<DashboardDTO> getAllDashboardComponent(
//            Principal principal
//    ) {
//        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
////        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");
//        Role role = employee.getRole();
//
//        List<Rekomendasi> daftarRekomendasi;
//        List<TemuanRisiko> daftarTemuanRisiko;
//        List<TugasPemeriksaan> daftarTugasPemeriksaan;
//        if (role.getNamaRole().equals("Branch Manager")) {
//            KantorCabang kantorCabang =
//                    kantorCabangRestService.getByPemilik(employee);
//            daftarTugasPemeriksaan =
//                    tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
//            List<HasilPemeriksaan> daftarHasilPemeriksaan =
//                    hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
//            List<KomponenPemeriksaan> daftarKomponenPemeriksaan =
//                    komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
//            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
//            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
//        } else {
//            daftarRekomendasi = rekomendasiRestService.getAll();
//            daftarTemuanRisiko = temuanRisikoRestService.getAll();
//            daftarTugasPemeriksaan = tugasPemeriksaanRestService.getAll();
//        }
//
//        DashboardDTO dashboardDTO = new DashboardDTO();
//        dashboardDTO.setJumlahPemeriksaan(daftarTugasPemeriksaan.size());
//        dashboardDTO.setJumlahRekomendasi(daftarRekomendasi.size());
//        dashboardDTO.setJumlahTemuan(daftarTemuanRisiko.size());
//
//        List<String> listTemuanApril = new ArrayList<String>();
//        List<String> listTemuanMei = new ArrayList<String>();
//        for (TemuanRisiko temuanRisiko : daftarTemuanRisiko) {
//            String tanggalTemuan = temuanRisiko.getKomponenPemeriksaan().getHasilPemeriksaan()
//                    .getTugasPemeriksaan().getTanggalSelesai().toString().substring(5,7);
//            if (tanggalTemuan.contains("04")) {
//                listTemuanApril.add(tanggalTemuan);
//            } else if (tanggalTemuan.contains("05")) {
//                listTemuanMei.add(tanggalTemuan);
//            }
//        }
//        List<Integer> jumlahTemuanBulanTertentu = new ArrayList<Integer>();
//        jumlahTemuanBulanTertentu.add(listTemuanApril.size());
//        jumlahTemuanBulanTertentu.add(listTemuanMei.size());
//        dashboardDTO.setJumlahTemuanPerBulan(jumlahTemuanBulanTertentu);
//
//        List<BuktiPelaksanaan> buktiList = buktiPelaksanaanRestService.getByDaftarRekomendasi(daftarRekomendasi);
//        List<BuktiPelaksanaan> listRekomendasiImplemented = new ArrayList<BuktiPelaksanaan>();
//        List<BuktiPelaksanaan> listRekomendasiNotImplemented = new ArrayList<BuktiPelaksanaan>();
//        List<Rekomendasi> listRekomendasiOverdue = new ArrayList<Rekomendasi>();
//
//        LocalDate waktuSaatIni = LocalDate.now();
//        for (Rekomendasi rekomendasi : daftarRekomendasi) {
//            LocalDate tenggatWaktu = rekomendasi.getTenggatWaktu();
//            if (tenggatWaktu != null) {
//                int durasi = (int) ChronoUnit.DAYS.between(waktuSaatIni, tenggatWaktu);
//                if (durasi == 0 | durasi < 0) {
//                    listRekomendasiOverdue.add(rekomendasi);
//                }
//            }
//            for (BuktiPelaksanaan buktiPelaksanaan : buktiList) {
//                if (buktiPelaksanaan.getRekomendasi().equals(rekomendasi)) {
//                    if (buktiPelaksanaan.getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
//                        listRekomendasiImplemented.add(buktiPelaksanaan);
//                    } else {
//                        listRekomendasiNotImplemented.add(buktiPelaksanaan);
//                    }
//                }
//            }
//        }
//
//        int rekomendasiOverdue = listRekomendasiOverdue.size()
//                - (listRekomendasiNotImplemented.size() + listRekomendasiImplemented.size());
//        int rekomendasiImplemented = listRekomendasiImplemented.size();
//        int rekomendasiNotImplemented = listRekomendasiNotImplemented.size()
//                + (daftarRekomendasi.size() - buktiList.size());
//
//        dashboardDTO.setJumlahRekomendasiOverdue(rekomendasiOverdue);
//        dashboardDTO.setJumlahRekomendasiImplemented(rekomendasiImplemented);
//        dashboardDTO.setJumlahRekomendasiNotImplemented(rekomendasiNotImplemented);
//
//        List<String> listRekomendasiOverdueApril = new ArrayList<String>();
//        List<String> listRekomendasiOverdueMei = new ArrayList<String>();
//        for (Rekomendasi rekomendasi : listRekomendasiOverdue) {
//            String tanggalRekomendasiOverdue = rekomendasi.getTenggatWaktu().toString().substring(5,7);
//            if (tanggalRekomendasiOverdue.contains("04")) {
//                listRekomendasiOverdueApril.add(tanggalRekomendasiOverdue);
//            } else if (tanggalRekomendasiOverdue.contains("05")) {
//                listRekomendasiOverdueMei.add(tanggalRekomendasiOverdue);
//            }
//        }
//        List<Integer> jumlahRekomendasiOverdueBulanTertentu = new ArrayList<Integer>();
//        jumlahRekomendasiOverdueBulanTertentu.add(listRekomendasiOverdueApril.size()
//                - (listRekomendasiNotImplemented.size() + listRekomendasiImplemented.size()));
//        jumlahRekomendasiOverdueBulanTertentu.add(listRekomendasiOverdueMei.size());
//        dashboardDTO.setJumlahRekomendasiOverduePerBulan(jumlahRekomendasiOverdueBulanTertentu);
//
//        List<String> listRekomendasiImplementedApril = new ArrayList<String>();
//        List<String> listRekomendasiImplementedMei = new ArrayList<String>();
//        for (BuktiPelaksanaan buktiPelaksanaan : listRekomendasiImplemented) {
//            String tanggalRekomendasiImplemented = buktiPelaksanaan.getRekomendasi().getTenggatWaktu().toString().substring(5,7);
//            if (tanggalRekomendasiImplemented.contains("04")) {
//                listRekomendasiImplementedApril.add(tanggalRekomendasiImplemented);
//            } else if (tanggalRekomendasiImplemented.contains("05")) {
//                listRekomendasiImplementedMei.add(tanggalRekomendasiImplemented);
//            }
//        }
//        List<Integer> jumlahRekomendasiImplementedBulanTertentu = new ArrayList<Integer>();
//        jumlahRekomendasiImplementedBulanTertentu.add(listRekomendasiImplementedApril.size());
//        jumlahRekomendasiImplementedBulanTertentu.add(listRekomendasiImplementedMei.size());
//        dashboardDTO.setJumlahRekomendasiImplementedPerBulan(jumlahRekomendasiImplementedBulanTertentu);
//
//        List<String> listRekomendasiNotImplementedApril = new ArrayList<String>();
//        List<String> listRekomendasiNotImplementedMei = new ArrayList<String>();
//        for (BuktiPelaksanaan buktiPelaksanaan : listRekomendasiNotImplemented) {
//            String tanggalRekomendasiNotImplemented = buktiPelaksanaan.getRekomendasi().getTenggatWaktu().toString().substring(5,7);
//            if (tanggalRekomendasiNotImplemented.contains("04")) {
//                listRekomendasiNotImplementedApril.add(tanggalRekomendasiNotImplemented);
//            } else if (tanggalRekomendasiNotImplemented.contains("05")) {
//                listRekomendasiNotImplementedMei.add(tanggalRekomendasiNotImplemented);
//            }
//        }
//        List<Integer> jumlahRekomendasiNotImplementedBulanTertentu = new ArrayList<Integer>();
//        jumlahRekomendasiNotImplementedBulanTertentu.add(listRekomendasiNotImplementedApril.size());
//        jumlahRekomendasiNotImplementedBulanTertentu.add(listRekomendasiNotImplementedMei.size()
//                + (daftarRekomendasi.size() - buktiList.size() - listRekomendasiNotImplementedApril.size()));
//        dashboardDTO.setJumlahRekomendasiNotImplementedPerBulan(jumlahRekomendasiNotImplementedBulanTertentu);
//
//        dashboardDTO.setPersenRekomendasiOverdue((float) rekomendasiOverdue*100/daftarRekomendasi.size());
//        dashboardDTO.setPersenRekomendasiImplemented((float) rekomendasiImplemented*100/daftarRekomendasi.size());
//        dashboardDTO.setPersenRekomendasiNotImplemented((float) rekomendasiNotImplemented*100/daftarRekomendasi.size());
//
//        return new BaseResponse<>(200, "success", dashboardDTO);
//    }

    @GetMapping("/getAll")
    private BaseResponse<DashboardDTO> getAllDashboardComponent(Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
//        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");
        Role role = employee.getRole();

        List<Rekomendasi> daftarRekomendasi;
        List<TemuanRisiko> daftarTemuanRisiko;
        List<TugasPemeriksaan> daftarTugasPemeriksaan;
        List<KomponenPemeriksaan> daftarKomponenPemeriksaan;
        if (role.getNamaRole().equals("Branch Manager")) {
            KantorCabang kantorCabang =
                    kantorCabangRestService.getByPemilik(employee);
            daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
            List<HasilPemeriksaan> daftarHasilPemeriksaan =
                    hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            daftarKomponenPemeriksaan =
                    komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        } else {
            daftarRekomendasi = rekomendasiRestService.getAll();
            daftarTemuanRisiko = temuanRisikoRestService.getAll();
            daftarTugasPemeriksaan = tugasPemeriksaanRestService.getAll();
            daftarKomponenPemeriksaan = komponenPemeriksaanRestService.getAll();
        }

        DashboardDTO result = new DashboardDTO();
        result.setJumlahPemeriksaan(daftarTugasPemeriksaan.size());
        result.setJumlahTemuan(daftarTemuanRisiko.size());
        List<Integer> listTemuan = temuanRisikoRestService.getTemuanByMonth(daftarTemuanRisiko);
        result.setJumlahTemuanPerBulan(listTemuan);

        List<Rekomendasi> rekomendasiOverdue = new ArrayList<>();
        List<Rekomendasi> rekomendasiImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiNotImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiTotal = new ArrayList<>();
        for (Rekomendasi rekomendasi : daftarRekomendasi) {
            if (rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Sedang Dilaksanakan")
                || rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Selesai")) {
                if (rekomendasi.getBuktiPelaksanaan() == null
                    || !(rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui"))
                    || (rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Menunggu Persetujuan")
                    && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Ditolak"))) {
                    if (rekomendasi.getTenggatWaktu().isBefore(LocalDate.now())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiNotImpl.add(rekomendasi);
                    }
                } else if (rekomendasi.getBuktiPelaksanaan() != null
                    && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
                    if (rekomendasi.getTenggatWaktu().isBefore(rekomendasi.getBuktiPelaksanaan().getTanggalPersetujuan())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiImpl.add(rekomendasi);
                    }
                }
                rekomendasiTotal.add(rekomendasi);
            }
        }
        result.setJumlahRekomendasi(rekomendasiTotal.size());

        List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue);
        result.setJumlahRekomendasiOverduePerBulan(listRekomendasiOverdue);
        double jumlahRekomendasiOverdue = roundAvoid((double)rekomendasiOverdue.size()*100/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);

        List<Integer> listRekomendasiImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiImpl);
        result.setJumlahRekomendasiImplementedPerBulan(listRekomendasiImpl);
        double jumlahRekomendasiImpl = roundAvoid((double)rekomendasiImpl.size()*100/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiImplemented(jumlahRekomendasiImpl);

        List<Integer> listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiNotImpl);
        result.setJumlahRekomendasiNotImplementedPerBulan(listRekomendasiNotImpl);
        double jumlahRekomendasiNotImpl = roundAvoid((double)rekomendasiNotImpl.size()*100/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiNotImplemented(jumlahRekomendasiNotImpl);

        int riskScore = 100;
        for (KomponenPemeriksaan komponen : daftarKomponenPemeriksaan) {
            riskScore += komponen.getRiskLevel().getBobotLevel();
        }
        result.setRiskScore(riskScore);

        result.setDaftarBulan(rekomendasiRestService.getListMonth());

        return new BaseResponse<>(200, "success", result);
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @GetMapping("/getAllByFilter")
    private BaseResponse<DashboardDTO> getDashboardComponentByFilter(
            @RequestParam("namaKantor") String namaKantor,
            @RequestParam("areaKantor") String areaKantor,
            @RequestParam("regionalKantor") String regionalKantor,
            @RequestParam("tanggalAwal") LocalDate tanggalAwal,
            @RequestParam("tanggalAkhir") LocalDate tanggalAkhir,
            Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
//        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");
        Role role = employee.getRole();

        List<Rekomendasi> daftarRekomendasi;
        List<TemuanRisiko> daftarTemuanRisiko;
        List<TugasPemeriksaan> daftarTugasPemeriksaan;
        List<KomponenPemeriksaan> daftarKomponenPemeriksaan;
        if (!namaKantor.isEmpty()) {
            Optional<KantorCabang> kantorCabang =
                    kantorCabangRestService.getByNama(namaKantor);
            daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService.getByKantorCabang(kantorCabang.get());
        } else if (!areaKantor.isEmpty() && !regionalKantor.isEmpty()) {
            List<KantorCabang> daftarKantorCabang =
                    kantorCabangRestService.getByAreaAndRegional(areaKantor, regionalKantor);
            daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
        } else if (!areaKantor.isEmpty()) {
            List<KantorCabang> daftarKantorCabang =
                    kantorCabangRestService.getByArea(areaKantor);
            daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
        } else {
            List<KantorCabang> daftarKantorCabang =
                    kantorCabangRestService.getByRegional(regionalKantor);
            daftarTugasPemeriksaan =
                    tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
        }
        List<HasilPemeriksaan> daftarHasilPemeriksaan =
                hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
        daftarKomponenPemeriksaan =
                komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
        daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);

        DashboardDTO result = new DashboardDTO();
        result.setJumlahPemeriksaan(daftarTugasPemeriksaan.size());
        result.setJumlahTemuan(daftarTemuanRisiko.size());
        List<Integer> listTemuan = temuanRisikoRestService.getTemuanByMonth(daftarTemuanRisiko);
        result.setJumlahTemuanPerBulan(listTemuan);

        List<Rekomendasi> rekomendasiOverdue = new ArrayList<>();
        List<Rekomendasi> rekomendasiImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiNotImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiTotal = new ArrayList<>();
        for (Rekomendasi rekomendasi : daftarRekomendasi) {
            if (rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Sedang Dilaksanakan")
                    || rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Selesai")) {
                if (rekomendasi.getBuktiPelaksanaan() == null
                        || !(rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui"))
                        || (rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Menunggu Persetujuan")
                        && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Ditolak"))) {
                    if (rekomendasi.getTenggatWaktu().isBefore(LocalDate.now())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiNotImpl.add(rekomendasi);
                    }
                } else if (rekomendasi.getBuktiPelaksanaan() != null
                        && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
                    if (rekomendasi.getTenggatWaktu().isBefore(rekomendasi.getBuktiPelaksanaan().getTanggalPersetujuan())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiImpl.add(rekomendasi);
                    }
                }
                rekomendasiTotal.add(rekomendasi);
            }
        }
        result.setJumlahRekomendasi(rekomendasiTotal.size());

        List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue);
        result.setJumlahRekomendasiOverduePerBulan(listRekomendasiOverdue);
        double jumlahRekomendasiOverdue = roundAvoid((double)rekomendasiOverdue.size()*100/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);

        List<Integer> listRekomendasiImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiImpl);
        result.setJumlahRekomendasiImplementedPerBulan(listRekomendasiImpl);
        double jumlahRekomendasiImpl = roundAvoid((double)rekomendasiImpl.size()*100/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiImplemented(jumlahRekomendasiImpl);

        List<Integer> listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiNotImpl);
        result.setJumlahRekomendasiNotImplementedPerBulan(listRekomendasiNotImpl);
        double jumlahRekomendasiNotImpl = roundAvoid((double)rekomendasiNotImpl.size()*100/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiNotImplemented(jumlahRekomendasiNotImpl);

        int riskScore = 100;
        for (KomponenPemeriksaan komponen : daftarKomponenPemeriksaan) {
            riskScore += komponen.getRiskLevel().getBobotLevel();
        }
        result.setRiskScore(riskScore);

        result.setDaftarBulan(rekomendasiRestService.getListMonth());

        return new BaseResponse<>(200, "success", result);
    }

}
