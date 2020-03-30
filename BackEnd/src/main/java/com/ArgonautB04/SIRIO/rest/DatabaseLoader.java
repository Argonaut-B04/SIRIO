package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.repository.*;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB;
    private final StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;
    private final StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB;
    private final StatusRekomendasiDB statusRekomendasiDB;
    private final EmployeeRestService employeeRestService;
    private final RoleDB roleDB;

    public DatabaseLoader(StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB, StatusHasilPemeriksaanDB statusHasilPemeriksaanDB, StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB, StatusRisikoDB statusRisikoDB, StatusRekomendasiDB statusRekomendasiDB, EmployeeRestService employeeRestService, RoleDB roleDB) {
        this.statusBuktiPelaksanaanDB = statusBuktiPelaksanaanDB;
        this.statusHasilPemeriksaanDB = statusHasilPemeriksaanDB;
        this.statusRencanaPemeriksaanDB = statusRencanaPemeriksaanDB;
        this.statusRekomendasiDB = statusRekomendasiDB;
        this.employeeRestService = employeeRestService;
        this.roleDB = roleDB;
    }

    @Override
    public void run(String... args) throws Exception {
        if (statusBuktiPelaksanaanDB.findAll().isEmpty()) populasiStatusBuktiPelaksanaan();
        if (statusRencanaPemeriksaanDB.findAll().isEmpty()) populasiStatusRencanaPemeriksaan();
        if (statusHasilPemeriksaanDB.findAll().isEmpty()) populasiStatusHasilPemeriksaan();
        if (statusRekomendasiDB.findAll().isEmpty()) populasiStatusRekomendasi();
        if (employeeRestService.getAll().isEmpty()) populasiEmployeedanRole();
    }

    private void populasiEmployeedanRole() {
        Role roleAdmin = new Role();
        roleAdmin.setNamaRole("admin");
        roleDB.save(roleAdmin);

        Role roleAdministrator = new Role();
        roleAdministrator.setNamaRole("Administrator");
        roleDB.save(roleAdministrator);

        Role roleSupervisor = new Role();
        roleSupervisor.setNamaRole("Supervisor");
        roleDB.save(roleSupervisor);

        Role roleManajer = new Role();
        roleManajer.setNamaRole("Manajer Operational Risk");
        roleDB.save(roleManajer);

        Role roleLead = new Role();
        roleLead.setNamaRole("QA Lead Operational Risk");
        roleDB.save(roleLead);

        Role roleOfficer = new Role();
        roleOfficer.setNamaRole("QA Officer Operational Risk");
        roleDB.save(roleOfficer);

        Role roleBM = new Role();
        roleBM.setNamaRole("Branch Manager");
        roleDB.save(roleBM);

        Role roleSuperOfficer = new Role();
        roleSuperOfficer.setNamaRole("Super QA Officer Operational Risk");
        roleDB.save(roleSuperOfficer);

        Employee admin = new Employee();
        admin.setEmail("admin@admin.com");
        admin.setNama("admin");
        admin.setUsername("admin");
        admin.setStatus(Employee.Status.AKTIF);
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        admin.setNoHp("123");
        employeeRestService.buatEmployee(admin);
    }

    private void populasiStatusBuktiPelaksanaan() {
        StatusBuktiPelaksanaan menungguPersetujuan = new StatusBuktiPelaksanaan();
        menungguPersetujuan.setNamaStatus("Menunggu Persetujuan");
        statusBuktiPelaksanaanDB.save(menungguPersetujuan);

        StatusBuktiPelaksanaan disetujui = new StatusBuktiPelaksanaan();
        disetujui.setNamaStatus("Disetujui");
        statusBuktiPelaksanaanDB.save(disetujui);

        StatusBuktiPelaksanaan ditolak = new StatusBuktiPelaksanaan();
        ditolak.setNamaStatus("Ditolak");
        statusBuktiPelaksanaanDB.save(ditolak);
    }

    private void populasiStatusRencanaPemeriksaan() {
        StatusRencanaPemeriksaan draft = new StatusRencanaPemeriksaan();
        draft.setNamaStatus("Draft");
        statusRencanaPemeriksaanDB.save(draft);

        StatusRencanaPemeriksaan sedangDijalankan = new StatusRencanaPemeriksaan();
        sedangDijalankan.setNamaStatus("Sedang Dijalankan");
        statusRencanaPemeriksaanDB.save(sedangDijalankan);

        StatusRencanaPemeriksaan selesai = new StatusRencanaPemeriksaan();
        selesai.setNamaStatus("Selesai");
        statusRencanaPemeriksaanDB.save(selesai);
    }

    private void populasiStatusHasilPemeriksaan() {
        StatusHasilPemeriksaan draft = new StatusHasilPemeriksaan();
        draft.setNamaStatus("Draft");
        statusHasilPemeriksaanDB.save(draft);

        StatusHasilPemeriksaan menungguPersetujuan = new StatusHasilPemeriksaan();
        menungguPersetujuan.setNamaStatus("Menunggu Persetujuan");
        statusHasilPemeriksaanDB.save(menungguPersetujuan);

        StatusHasilPemeriksaan ditolak = new StatusHasilPemeriksaan();
        ditolak.setNamaStatus("Ditolak");
        statusHasilPemeriksaanDB.save(ditolak);

        StatusHasilPemeriksaan menungguPelaksanaan = new StatusHasilPemeriksaan();
        menungguPelaksanaan.setNamaStatus("Menunggu Pelaksanaan");
        statusHasilPemeriksaanDB.save(menungguPelaksanaan);

        StatusHasilPemeriksaan selesai = new StatusHasilPemeriksaan();
        selesai.setNamaStatus("Selesai");
        statusHasilPemeriksaanDB.save(selesai);
    }

    private void populasiStatusRekomendasi() {
        StatusRekomendasi draft = new StatusRekomendasi();
        draft.setNamaStatus("Draft");
        draft.setDapatSetTenggatWaktu(false);
        statusRekomendasiDB.save(draft);

        StatusRekomendasi menungguPersetujuan = new StatusRekomendasi();
        menungguPersetujuan.setNamaStatus("Menunggu Persetujuan");
        menungguPersetujuan.setDapatSetTenggatWaktu(false);
        statusRekomendasiDB.save(menungguPersetujuan);

        StatusRekomendasi ditolak = new StatusRekomendasi();
        ditolak.setNamaStatus("Ditolak");
        ditolak.setDapatSetTenggatWaktu(false);
        statusRekomendasiDB.save(ditolak);

        StatusRekomendasi menungguPengaturanTenggatWaktu = new StatusRekomendasi();
        menungguPengaturanTenggatWaktu.setNamaStatus("Menunggu Pengaturan Tenggat Waktu");
        menungguPengaturanTenggatWaktu.setDapatSetTenggatWaktu(true);
        statusRekomendasiDB.save(menungguPengaturanTenggatWaktu);

        StatusRekomendasi menungguPelaksanaan = new StatusRekomendasi();
        menungguPelaksanaan.setNamaStatus("Menunggu Pelaksanaan");
        menungguPelaksanaan.setDapatSetTenggatWaktu(true);
        statusRekomendasiDB.save(menungguPelaksanaan);

        StatusRekomendasi sedangDilaksanakan = new StatusRekomendasi();
        sedangDilaksanakan.setNamaStatus("Sedang Dilaksanakan");
        sedangDilaksanakan.setDapatSetTenggatWaktu(false);
        statusRekomendasiDB.save(sedangDilaksanakan);

        StatusRekomendasi selesai = new StatusRekomendasi();
        selesai.setNamaStatus("Selesai");
        selesai.setDapatSetTenggatWaktu(false);
        statusRekomendasiDB.save(selesai);
    }
}
