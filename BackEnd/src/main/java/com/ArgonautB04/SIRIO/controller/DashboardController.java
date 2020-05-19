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
import org.springframework.util.PatternMatchUtils;
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

        result.setDaftarBulan(getListMonth());

        return new BaseResponse<>(200, "success", result);
    }

//    @GetMapping("/getAllByFilter")
//    private BaseResponse<DashboardDTO> getDashboardComponentByFilter(
//            @RequestParam("namaKantor") String namaKantor,
//            @RequestParam("areaKantor") String areaKantor,
//            @RequestParam("regionalKantor") String regionalKantor,
//            Principal principal) {
//        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
////        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");
//        Role role = employee.getRole();
//
//        List<Rekomendasi> daftarRekomendasi;
//        List<TemuanRisiko> daftarTemuanRisiko;
//        List<TugasPemeriksaan> daftarTugasPemeriksaan;
//        List<KomponenPemeriksaan> daftarKomponenPemeriksaan;
//        if (!namaKantor.isEmpty()) {
//            Optional<KantorCabang> kantorCabang =
//                    kantorCabangRestService.getByNama(namaKantor);
//            daftarTugasPemeriksaan =
//                    tugasPemeriksaanRestService.getByKantorCabang(kantorCabang.get());
//        } else if (!areaKantor.isEmpty() && !regionalKantor.isEmpty()) {
//            List<KantorCabang> daftarKantorCabang =
//                    kantorCabangRestService.getByAreaAndRegional(areaKantor, regionalKantor);
//            daftarTugasPemeriksaan =
//                    tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
//        } else if (!areaKantor.isEmpty()) {
//            List<KantorCabang> daftarKantorCabang =
//                    kantorCabangRestService.getByArea(areaKantor);
//            daftarTugasPemeriksaan =
//                    tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
//        } else {
//            List<KantorCabang> daftarKantorCabang =
//                    kantorCabangRestService.getByRegional(regionalKantor);
//            daftarTugasPemeriksaan =
//                    tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
//        }
//        List<HasilPemeriksaan> daftarHasilPemeriksaan =
//                hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
//        daftarKomponenPemeriksaan =
//                komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
//        daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
//        daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
//
//        DashboardDTO result = new DashboardDTO();
//        result.setJumlahPemeriksaan(daftarTugasPemeriksaan.size());
//        result.setJumlahTemuan(daftarTemuanRisiko.size());
//        List<Integer> listTemuan = temuanRisikoRestService.getTemuanByMonth(daftarTemuanRisiko);
//        result.setJumlahTemuanPerBulan(listTemuan);
//
//        List<Rekomendasi> rekomendasiOverdue = new ArrayList<>();
//        List<Rekomendasi> rekomendasiImpl = new ArrayList<>();
//        List<Rekomendasi> rekomendasiNotImpl = new ArrayList<>();
//        List<Rekomendasi> rekomendasiTotal = new ArrayList<>();
//        for (Rekomendasi rekomendasi : daftarRekomendasi) {
//            if (rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Sedang Dilaksanakan")
//                    || rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Selesai")) {
//                if (rekomendasi.getBuktiPelaksanaan() == null
//                        || !(rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui"))
//                        || (rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Menunggu Persetujuan")
//                        && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Ditolak"))) {
//                    if (rekomendasi.getTenggatWaktu().isBefore(LocalDate.now())) {
//                        rekomendasiOverdue.add(rekomendasi);
//                    } else {
//                        rekomendasiNotImpl.add(rekomendasi);
//                    }
//                } else if (rekomendasi.getBuktiPelaksanaan() != null
//                        && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
//                    if (rekomendasi.getTenggatWaktu().isBefore(rekomendasi.getBuktiPelaksanaan().getTanggalPersetujuan())) {
//                        rekomendasiOverdue.add(rekomendasi);
//                    } else {
//                        rekomendasiImpl.add(rekomendasi);
//                    }
//                }
//                rekomendasiTotal.add(rekomendasi);
//            }
//        }
//        result.setJumlahRekomendasi(rekomendasiTotal.size());
//
//        List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue);
//        result.setJumlahRekomendasiOverduePerBulan(listRekomendasiOverdue);
//        double jumlahRekomendasiOverdue = roundAvoid((double)rekomendasiOverdue.size()*100/(double)daftarRekomendasi.size(), 2);
//        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);
//
//        List<Integer> listRekomendasiImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiImpl);
//        result.setJumlahRekomendasiImplementedPerBulan(listRekomendasiImpl);
//        double jumlahRekomendasiImpl = roundAvoid((double)rekomendasiImpl.size()*100/(double)daftarRekomendasi.size(), 2);
//        result.setJumlahRekomendasiImplemented(jumlahRekomendasiImpl);
//
//        List<Integer> listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiNotImpl);
//        result.setJumlahRekomendasiNotImplementedPerBulan(listRekomendasiNotImpl);
//        double jumlahRekomendasiNotImpl = roundAvoid((double)rekomendasiNotImpl.size()*100/(double)daftarRekomendasi.size(), 2);
//        result.setJumlahRekomendasiNotImplemented(jumlahRekomendasiNotImpl);
//
//        int riskScore = 100;
//        for (KomponenPemeriksaan komponen : daftarKomponenPemeriksaan) {
//            riskScore += komponen.getRiskLevel().getBobotLevel();
//        }
//        result.setRiskScore(riskScore);
//
//        result.setDaftarBulan(rekomendasiRestService.getListMonth());
//
//        return new BaseResponse<>(200, "success", result);
//    }

    @GetMapping("/getAllByFilter")
    private BaseResponse<DashboardDTO> getDashboardComponentByFilter(
            @RequestParam("namaKantor") String namaKantor,
            @RequestParam("areaKantor") String areaKantor,
            @RequestParam("regionalKantor") String regionalKantor,
            @RequestParam("tanggalPertama") String tanggalPertama,
            @RequestParam("tanggalKedua") String tanggalKedua,
            Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
//        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");
        Role role = employee.getRole();

        List<Rekomendasi> daftarRekomendasi;
        List<TemuanRisiko> daftarTemuanRisiko;
        List<TugasPemeriksaan> daftarTugasPemeriksaan;
        List<HasilPemeriksaan> daftarHasilPemeriksaan;
        List<KomponenPemeriksaan> daftarKomponenPemeriksaan;

        LocalDate tanggalAwal = null;
        LocalDate tanggalAkhir = null;
        if (tanggalPertama.isEmpty() && tanggalKedua.isEmpty()) {
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
            daftarHasilPemeriksaan =
                    hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            daftarKomponenPemeriksaan =
                    komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        } else {
            if (tanggalKedua.isEmpty()) {
                tanggalKedua = (LocalDate.parse(tanggalPertama).plusMonths(5)).toString();
            } else if (tanggalPertama.isEmpty()) {
                tanggalPertama = (LocalDate.parse(tanggalKedua).minusMonths(5)).toString();
            }
            tanggalAwal = LocalDate.parse(tanggalPertama);
            tanggalAkhir = LocalDate.parse(tanggalKedua);
            if (namaKantor.isEmpty() && areaKantor.isEmpty() && regionalKantor.isEmpty()) {
                List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getAll();
                daftarTugasPemeriksaan =
                        tugasPemeriksaanRestService.getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
            } else if (!namaKantor.isEmpty()) {
                Optional<KantorCabang> kantorCabang =
                        kantorCabangRestService.getByNama(namaKantor);
                daftarTugasPemeriksaan =
                        tugasPemeriksaanRestService.getByKantorCabangAndTanggalSelesai(kantorCabang.get(), tanggalAwal, tanggalAkhir);
            } else if (!areaKantor.isEmpty() && !regionalKantor.isEmpty()) {
                List<KantorCabang> daftarKantorCabang =
                        kantorCabangRestService.getByAreaAndRegional(areaKantor, regionalKantor);
                daftarTugasPemeriksaan =
                        tugasPemeriksaanRestService.getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
            } else if (!areaKantor.isEmpty()) {
                List<KantorCabang> daftarKantorCabang =
                        kantorCabangRestService.getByArea(areaKantor);
                daftarTugasPemeriksaan =
                        tugasPemeriksaanRestService.getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
            } else {
                List<KantorCabang> daftarKantorCabang =
                        kantorCabangRestService.getByRegional(regionalKantor);
                daftarTugasPemeriksaan =
                        tugasPemeriksaanRestService.getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
            }
            daftarHasilPemeriksaan =
                    hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            daftarKomponenPemeriksaan =
                    komponenPemeriksaanRestService.getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaanAndTenggatWaktu(
                    daftarKomponenPemeriksaan, tanggalAwal, tanggalAkhir);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        }

        DashboardDTO result = new DashboardDTO();
        result.setTugasPemeriksaan(daftarTugasPemeriksaan);
        result.setHasilPemeriksaan(daftarHasilPemeriksaan);
        result.setRekomendasi(daftarRekomendasi);
        result.setAwal(tanggalAwal);
        result.setAkhir(tanggalAkhir);

        result.setJumlahPemeriksaan(daftarTugasPemeriksaan.size());
        result.setJumlahTemuan(daftarTemuanRisiko.size());

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

        List<Integer> listRekomendasiOverdue = new ArrayList<>();
        List<Integer> listRekomendasiImpl = new ArrayList<>();
        List<Integer> listRekomendasiNotImpl = new ArrayList<>();
        List<Integer> listTemuan = new ArrayList<>();

        LocalDate awal = tanggalAwal;
        if (tanggalPertama.isEmpty() && tanggalKedua.isEmpty()) {
            listRekomendasiOverdue = rekomendasiRestService.getRekomendasiByMonth(rekomendasiOverdue);
            listRekomendasiImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiImpl);
            listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiByMonth(rekomendasiNotImpl);
            listTemuan = temuanRisikoRestService.getTemuanByMonth(daftarTemuanRisiko);
            result.setDaftarBulan(getListMonth());
        } else {
//            if (awal.isEqual(tanggalAkhir.minusMonths(5))) {
//                awal = awal.minusMonths(1);
//            }
            while (awal.isBefore(tanggalAkhir) || awal.isEqual(tanggalAkhir)) {
                int counterOverdue = 0;
                int counterImpl = 0;
                int counterNotImpl = 0;
                int counterTemuan = 0;
                for (Rekomendasi r : rekomendasiOverdue) {
                    if (!r.getTenggatWaktu().isBefore(awal) && !r.getTenggatWaktu().isAfter(awal.plusMonths(1))) {
                        counterOverdue += 1;
                    }
                }
                for (Rekomendasi r : rekomendasiImpl) {
                    if (!r.getTenggatWaktu().isBefore(awal) && !r.getTenggatWaktu().isAfter(awal.plusMonths(1))) {
                        counterImpl += 1;
                    }
                }
                for (Rekomendasi r : rekomendasiNotImpl) {
                    if (!r.getTenggatWaktu().isBefore(awal) && !r.getTenggatWaktu().isAfter(awal.plusMonths(1))) {
                        counterNotImpl += 1;
                    }
                }
                for (TemuanRisiko tr : daftarTemuanRisiko) {
                    if (!tr.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalSelesai()
                            .isBefore(awal)
                            && !tr.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalSelesai()
                            .isAfter(awal.plusMonths(1))) {
                        counterTemuan += 1;
                    }
                }
                listRekomendasiOverdue.add(counterOverdue);
                listRekomendasiImpl.add(counterImpl);
                listRekomendasiNotImpl.add(counterNotImpl);
                listTemuan.add(counterTemuan);
                awal = awal.plusMonths(1);
            }
            result.setDaftarBulan(getListMonthFiltered(tanggalAwal, tanggalAkhir));
        }

        result.setJumlahRekomendasiOverduePerBulan(listRekomendasiOverdue);
        double jumlahRekomendasiOverdue = roundAvoid((double)(rekomendasiOverdue.size()*100)/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);

        result.setJumlahRekomendasiImplementedPerBulan(listRekomendasiImpl);
        double jumlahRekomendasiImpl = roundAvoid((double)(rekomendasiImpl.size()*100)/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiImplemented(jumlahRekomendasiImpl);

        result.setJumlahRekomendasiNotImplementedPerBulan(listRekomendasiNotImpl);
        double jumlahRekomendasiNotImpl = roundAvoid((double)(rekomendasiNotImpl.size()*100)/(double)daftarRekomendasi.size(), 2);
        result.setJumlahRekomendasiNotImplemented(jumlahRekomendasiNotImpl);

        result.setJumlahTemuanPerBulan(listTemuan);

        int riskScore = 100;
        for (KomponenPemeriksaan komponen : daftarKomponenPemeriksaan) {
            riskScore += komponen.getRiskLevel().getBobotLevel();
        }
        result.setRiskScore(riskScore);

        return new BaseResponse<>(200, "success", result);
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static int sumNumbers(List<Integer> numbers){
        return numbers.stream().mapToInt(n->n).sum();
    }

    public List<String> getListMonth() {
        List<String> months = new ArrayList<>(6);
        for (int i=5;i>=0;i--) {
            months.add(LocalDate.now().minusMonths(i).getMonth() + "\n" + LocalDate.now().minusMonths(i).getYear());
        }
        return months;
    }

    public List<String> getListMonthFiltered(LocalDate tanggalAwal, LocalDate tanggalAkhir) {
        int monthsBetween = (int) ChronoUnit.MONTHS.between(tanggalAwal, tanggalAkhir);
        List<String> months = new ArrayList<>();
        for (int i = monthsBetween; i >= 0; i--) {
            months.add(tanggalAkhir.minusMonths(i).getMonth() + "\n" + tanggalAkhir.minusMonths(i).getYear());
        }
        return months;
    }

}
