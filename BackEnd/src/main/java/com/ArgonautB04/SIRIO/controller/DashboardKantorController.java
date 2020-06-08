package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.KantorCabang;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Rekomendasi;
import com.argonautb04.sirio.model.Role;
import com.argonautb04.sirio.model.TemuanRisiko;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.rest.DashboardKantorDTO;
import com.argonautb04.sirio.services.BuktiPelaksanaanRestService;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.HasilPemeriksaanRestService;
import com.argonautb04.sirio.services.KantorCabangRestService;
import com.argonautb04.sirio.services.KomponenPemeriksaanRestService;
import com.argonautb04.sirio.services.RekomendasiRestService;
import com.argonautb04.sirio.services.TemuanRisikoRestService;
import com.argonautb04.sirio.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Dashboard")
public class DashboardKantorController {

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

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @GetMapping("/getAll")
    private BaseResponse<DashboardKantorDTO> getAllDashboardComponent(Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "dashboard kantor cabang");
        Role role = employee.getRole();

        List<Rekomendasi> daftarRekomendasi;
        List<TemuanRisiko> daftarTemuanRisiko;
        List<TugasPemeriksaan> daftarTugasPemeriksaan;
        List<HasilPemeriksaan> daftarHasilPemeriksaan;
        List<KomponenPemeriksaan> daftarKomponenPemeriksaan;
        if (role.getNamaRole().equals("Branch Manager")) {
            KantorCabang kantorCabang = kantorCabangRestService.getByPemilik(employee);
            daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
            daftarHasilPemeriksaan = hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            daftarKomponenPemeriksaan = komponenPemeriksaanRestService
                    .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        } else {
            daftarRekomendasi = rekomendasiRestService.getAll();
            daftarTemuanRisiko = temuanRisikoRestService.getAll();
            daftarTugasPemeriksaan = tugasPemeriksaanRestService.getAll();
            daftarHasilPemeriksaan = hasilPemeriksaanRestService.getAll();
            daftarKomponenPemeriksaan = komponenPemeriksaanRestService.getAll();
        }

        DashboardKantorDTO result = new DashboardKantorDTO();

        List<HasilPemeriksaan> hasilSelesai = new ArrayList<>();
        for (HasilPemeriksaan hasilPemeriksaan : daftarHasilPemeriksaan) {
            if (hasilPemeriksaan.getStatusHasilPemeriksaan().getNamaStatus().equals("Selesai")) {
                hasilSelesai.add(hasilPemeriksaan);
            }
        }
        result.setJumlahPemeriksaan(hasilSelesai.size());

        result.setJumlahTemuan(daftarTemuanRisiko.size());
        List<Integer> listTemuan = temuanRisikoRestService.getTemuanPerMonth(daftarTemuanRisiko);
        result.setJumlahTemuanPerBulan(listTemuan);

        List<Rekomendasi> rekomendasiOverdue = new ArrayList<>();
        List<Rekomendasi> rekomendasiImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiNotImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiTotal = new ArrayList<>();
        for (Rekomendasi rekomendasi : daftarRekomendasi) {
            if (rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Sedang Dilaksanakan")
                    || rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Selesai")) {
                if (rekomendasi.getBuktiPelaksanaan() == null
                        || !(rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus()
                        .equals("Disetujui"))
                        || (rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus()
                        .equals("Menunggu Persetujuan")
                        && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus()
                        .equals("Ditolak"))) {
                    if (rekomendasi.getTenggatWaktu().isBefore(LocalDate.now())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiNotImpl.add(rekomendasi);
                    }
                } else if (rekomendasi.getBuktiPelaksanaan() != null && rekomendasi.getBuktiPelaksanaan()
                        .getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
                    if (rekomendasi.getTenggatWaktu()
                            .isBefore(rekomendasi.getBuktiPelaksanaan().getTanggalPersetujuan())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiImpl.add(rekomendasi);
                    }
                }
                rekomendasiTotal.add(rekomendasi);
            }
        }
        result.setJumlahRekomendasi(rekomendasiTotal.size());

        List<Integer> listRekomendasiOverdue = rekomendasiRestService.getRekomendasiPerMonth(rekomendasiOverdue);
        result.setJumlahRekomendasiOverduePerBulan(listRekomendasiOverdue);
        double jumlahRekomendasiOverdue = roundAvoid(
                (double) rekomendasiOverdue.size() * 100 / (double) rekomendasiTotal.size(), 2);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);

        List<Integer> listRekomendasiImpl = rekomendasiRestService.getRekomendasiPerMonth(rekomendasiImpl);
        result.setJumlahRekomendasiImplementedPerBulan(listRekomendasiImpl);
        double jumlahRekomendasiImpl = roundAvoid(
                (double) rekomendasiImpl.size() * 100 / (double) rekomendasiTotal.size(), 2);
        result.setJumlahRekomendasiImplemented(jumlahRekomendasiImpl);

        List<Integer> listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiPerMonth(rekomendasiNotImpl);
        result.setJumlahRekomendasiNotImplementedPerBulan(listRekomendasiNotImpl);
        double jumlahRekomendasiNotImpl = roundAvoid(
                (double) rekomendasiNotImpl.size() * 100 / (double) rekomendasiTotal.size(), 2);
        result.setJumlahRekomendasiNotImplemented(jumlahRekomendasiNotImpl);

        int riskScore = 100;
        for (KomponenPemeriksaan komponen : daftarKomponenPemeriksaan) {
            if (komponen.getRiskLevel() != null) {
                riskScore += komponen.getRiskLevel().getBobotLevel();
            }
        }
        result.setRiskScore(riskScore);

        result.setDaftarBulan(getListMonth());

        return new BaseResponse<>(200, "success", result);
    }

    @GetMapping("/getAllByFilter")
    private BaseResponse<DashboardKantorDTO> getDashboardComponentByFilter(
            @RequestParam("namaKantor") String namaKantor, @RequestParam("areaKantor") String areaKantor,
            @RequestParam("regionalKantor") String regionalKantor,
            @RequestParam("tanggalPertama") String tanggalPertama, @RequestParam("tanggalKedua") String tanggalKedua,
            Principal principal) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "dashboard kantor cabang");
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
                Optional<KantorCabang> kantorCabang = kantorCabangRestService.getByNama(namaKantor);
                daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByKantorCabang(kantorCabang.get());
            } else if (!areaKantor.isEmpty() && !regionalKantor.isEmpty()) {
                List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getByAreaAndRegional(areaKantor,
                        regionalKantor);
                daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
            } else if (!areaKantor.isEmpty()) {
                List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getByArea(areaKantor);
                daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
            } else {
                List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getByRegional(regionalKantor);
                daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByDaftarKantorCabang(daftarKantorCabang);
            }
            daftarHasilPemeriksaan = hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            daftarKomponenPemeriksaan = komponenPemeriksaanRestService
                    .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        } else {
            tanggalAwal = LocalDate.parse(tanggalPertama);
            tanggalAkhir = LocalDate.parse(tanggalKedua);
            if (role.getNamaRole().equals("Branch Manager")) {
                KantorCabang kantorCabang = kantorCabangRestService.getByPemilik(employee);
                daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByKantorCabangAndTanggalSelesai(kantorCabang,
                        tanggalAwal, tanggalAkhir);
            } else {
                if (namaKantor.isEmpty() && areaKantor.isEmpty() && regionalKantor.isEmpty()) {
                    List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getAll();
                    daftarTugasPemeriksaan = tugasPemeriksaanRestService
                            .getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
                } else if (!namaKantor.isEmpty()) {
                    Optional<KantorCabang> kantorCabang = kantorCabangRestService.getByNama(namaKantor);
                    daftarTugasPemeriksaan = tugasPemeriksaanRestService
                            .getByKantorCabangAndTanggalSelesai(kantorCabang.get(), tanggalAwal, tanggalAkhir);
                } else if (!areaKantor.isEmpty() && !regionalKantor.isEmpty()) {
                    List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getByAreaAndRegional(areaKantor,
                            regionalKantor);
                    daftarTugasPemeriksaan = tugasPemeriksaanRestService
                            .getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
                } else if (!areaKantor.isEmpty()) {
                    List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getByArea(areaKantor);
                    daftarTugasPemeriksaan = tugasPemeriksaanRestService
                            .getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
                } else {
                    List<KantorCabang> daftarKantorCabang = kantorCabangRestService.getByRegional(regionalKantor);
                    daftarTugasPemeriksaan = tugasPemeriksaanRestService
                            .getByDaftarKantorCabangAndTanggalSelesai(daftarKantorCabang, tanggalAwal, tanggalAkhir);
                }
            }
            daftarHasilPemeriksaan = hasilPemeriksaanRestService.getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            daftarKomponenPemeriksaan = komponenPemeriksaanRestService
                    .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            daftarRekomendasi = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
            daftarTemuanRisiko = temuanRisikoRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
        }

        DashboardKantorDTO result = new DashboardKantorDTO();
        result.setJumlahTemuan(daftarTemuanRisiko.size());

        List<HasilPemeriksaan> hasilSelesai = new ArrayList<>();
        for (HasilPemeriksaan hasilPemeriksaan : daftarHasilPemeriksaan) {
            if (hasilPemeriksaan.getStatusHasilPemeriksaan().getNamaStatus().equals("Selesai")) {
                hasilSelesai.add(hasilPemeriksaan);
            }
        }
        result.setJumlahPemeriksaan(hasilSelesai.size());

        List<Rekomendasi> rekomendasiOverdue = new ArrayList<>();
        List<Rekomendasi> rekomendasiImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiNotImpl = new ArrayList<>();
        List<Rekomendasi> rekomendasiTotal = new ArrayList<>();
        for (Rekomendasi rekomendasi : daftarRekomendasi) {
            if (rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Sedang Dilaksanakan")
                    || rekomendasi.getStatusRekomendasi().getNamaStatus().equals("Selesai")) {
                if (rekomendasi.getBuktiPelaksanaan() == null
                        || !(rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus()
                        .equals("Disetujui"))
                        || (rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus()
                        .equals("Menunggu Persetujuan")
                        && rekomendasi.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getNamaStatus()
                        .equals("Ditolak"))) {
                    if (rekomendasi.getTenggatWaktu().isBefore(LocalDate.now())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiNotImpl.add(rekomendasi);
                    }
                } else if (rekomendasi.getBuktiPelaksanaan() != null && rekomendasi.getBuktiPelaksanaan()
                        .getStatusBuktiPelaksanaan().getNamaStatus().equals("Disetujui")) {
                    if (rekomendasi.getTenggatWaktu()
                            .isBefore(rekomendasi.getBuktiPelaksanaan().getTanggalPersetujuan())) {
                        rekomendasiOverdue.add(rekomendasi);
                    } else {
                        rekomendasiImpl.add(rekomendasi);
                    }
                }
                rekomendasiTotal.add(rekomendasi);
            }
        }
        result.setJumlahRekomendasi(rekomendasiTotal.size());

        List<Integer> listRekomendasiOverdue;
        List<Integer> listRekomendasiImpl;
        List<Integer> listRekomendasiNotImpl;
        List<Integer> listTemuan;
        if (tanggalPertama.isEmpty() && tanggalKedua.isEmpty()) {
            listRekomendasiOverdue = rekomendasiRestService.getRekomendasiPerMonth(rekomendasiOverdue);
            listRekomendasiImpl = rekomendasiRestService.getRekomendasiPerMonth(rekomendasiImpl);
            listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiPerMonth(rekomendasiNotImpl);
            listTemuan = temuanRisikoRestService.getTemuanPerMonth(daftarTemuanRisiko);
            result.setDaftarBulan(getListMonth());
        } else {
            listRekomendasiOverdue = rekomendasiRestService.getRekomendasiPerMonthFiltered(rekomendasiOverdue,
                    tanggalAwal, tanggalAkhir);
            listRekomendasiImpl = rekomendasiRestService.getRekomendasiPerMonthFiltered(rekomendasiImpl, tanggalAwal,
                    tanggalAkhir);
            listRekomendasiNotImpl = rekomendasiRestService.getRekomendasiPerMonthFiltered(rekomendasiNotImpl,
                    tanggalAwal, tanggalAkhir);
            listTemuan = temuanRisikoRestService.getTemuanPerMonthFiltered(daftarTemuanRisiko, tanggalAwal,
                    tanggalAkhir);
            result.setDaftarBulan(getListMonthFiltered(tanggalAwal, tanggalAkhir));
        }

        result.setJumlahRekomendasiOverduePerBulan(listRekomendasiOverdue);
        double jumlahRekomendasiOverdue = roundAvoid(
                (double) (rekomendasiOverdue.size() * 100) / (double) rekomendasiTotal.size(), 2);
        result.setJumlahRekomendasiOverdue(jumlahRekomendasiOverdue);

        result.setJumlahRekomendasiImplementedPerBulan(listRekomendasiImpl);
        double jumlahRekomendasiImpl = roundAvoid(
                (double) (rekomendasiImpl.size() * 100) / (double) rekomendasiTotal.size(), 2);
        result.setJumlahRekomendasiImplemented(jumlahRekomendasiImpl);

        result.setJumlahRekomendasiNotImplementedPerBulan(listRekomendasiNotImpl);
        double jumlahRekomendasiNotImpl = roundAvoid(
                (double) (rekomendasiNotImpl.size() * 100) / (double) rekomendasiTotal.size(), 2);
        result.setJumlahRekomendasiNotImplemented(jumlahRekomendasiNotImpl);

        result.setJumlahTemuanPerBulan(listTemuan);

        int riskScore = 100;
        for (KomponenPemeriksaan komponen : daftarKomponenPemeriksaan) {
            if (komponen.getRiskLevel() != null) {
                riskScore += komponen.getRiskLevel().getBobotLevel();
            }
        }
        result.setRiskScore(riskScore);

        return new BaseResponse<>(200, "success", result);
    }

    public List<String> getListMonth() {
        List<String> months = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            months.add(LocalDate.now().minusMonths(i).getMonth() + " " + LocalDate.now().minusMonths(i).getYear());
        }
        return months;
    }

    public List<String> getListMonthFiltered(LocalDate tanggalAwal, LocalDate tanggalAkhir) {
        List<String> months = new ArrayList<>();
        if (tanggalAwal != null) {
            int monthsBetween = (int) ChronoUnit.MONTHS.between(tanggalAwal.withDayOfMonth(1),
                    tanggalAkhir.withDayOfMonth(1));
            for (int i = monthsBetween; i >= 0; i--) {
                months.add(tanggalAkhir.minusMonths(i).getMonth() + " " + tanggalAkhir.minusMonths(i).getYear());
            }
        }
        return months;
    }

}
