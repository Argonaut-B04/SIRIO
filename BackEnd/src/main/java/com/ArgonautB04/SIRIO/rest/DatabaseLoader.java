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
    private final AccessPermissionDB accessPermissionDB;

    public DatabaseLoader(StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB, StatusHasilPemeriksaanDB statusHasilPemeriksaanDB, StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB, StatusRisikoDB statusRisikoDB, StatusRekomendasiDB statusRekomendasiDB, EmployeeRestService employeeRestService, RoleDB roleDB, AccessPermissionDB accessPermissionDB) {
        this.statusBuktiPelaksanaanDB = statusBuktiPelaksanaanDB;
        this.statusHasilPemeriksaanDB = statusHasilPemeriksaanDB;
        this.statusRencanaPemeriksaanDB = statusRencanaPemeriksaanDB;
        this.statusRekomendasiDB = statusRekomendasiDB;
        this.employeeRestService = employeeRestService;
        this.roleDB = roleDB;
        this.accessPermissionDB = accessPermissionDB;
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

        // Inisiasi Role
        Role roleAdministrator = roleDB.save(new Role("Administrator"));
        Role roleSupervisor = roleDB.save(new Role("Supervisor"));
        Role roleManajer = roleDB.save(new Role("Manajer Operational Risk"));
        Role roleLead = roleDB.save(new Role("QA Lead Operational Risk"));
        Role roleOfficer = roleDB.save(new Role("QA Officer Operational Risk"));
        Role roleBM = roleDB.save(new Role("Branch Manager"));
        Role roleSuper = roleDB.save(new Role("Super QA Officer Operational Risk"));
        Role roleDeveloper = roleDB.save(new Role("dev"));
        // Inisiasi Role Selesai


        // Inisiasi Akses
        // Akses Admin
        AccessPermissions aksesAdmin = new AccessPermissions(roleAdministrator);
        accessPermissionDB.save(aksesAdmin);
        // Akses Admin Selesai

        // Akses Supervisor
        AccessPermissions aksesSupervisor = new AccessPermissions(roleSupervisor);
        accessPermissionDB.save(aksesSupervisor);
        // Akses Supervisor Selesai

        // Akses Manajer
        AccessPermissions aksesManajer = new AccessPermissions(roleManajer);
        aksesManajer.setAksesRiskRating(true);
        aksesManajer.setUbahRiskRating(true);

        aksesManajer.setAksesRiskLevel(true);
        aksesManajer.setUbahRiskLevel(true);
        accessPermissionDB.save(aksesManajer);
        // Akses Manajer Selesai

        // Akses Lead
        AccessPermissions aksesLead = new AccessPermissions(roleLead);
        accessPermissionDB.save(aksesLead);
        // Akses Lead Selesai

        // Akses Officer
        AccessPermissions aksesOfficer = new AccessPermissions(roleOfficer);
        aksesOfficer.setAksesRisiko(true);
        aksesOfficer.setAksesTabelRisiko(true);

        aksesOfficer.setUbahReminder(true);
        accessPermissionDB.save(aksesOfficer);
        // Akses Officer Selesai

        // Akses BM
        AccessPermissions aksesBM = new AccessPermissions(roleBM);
        accessPermissionDB.save(aksesBM);
        // Akses BM Selesai

        // Akses SuperQA
        AccessPermissions aksesSuper = new AccessPermissions(roleSuper);
        aksesSuper.setAksesTambahRisiko(true);
        aksesSuper.setAksesHapusRisiko(true);
        aksesSuper.setAksesRisiko(true);
        aksesSuper.setAksesUbahRisiko(true);
        aksesSuper.setAksesTabelRisiko(true);

        aksesSuper.setAksesRiskRating(true);
        aksesSuper.setUbahRiskRating(true);

        aksesSuper.setAksesRiskLevel(true);
        aksesSuper.setUbahRiskLevel(true);

        aksesSuper.setUbahReminder(true);
        accessPermissionDB.save(aksesSuper);
        // Akses SuperQA Selesai

        // Akses Developer
        AccessPermissions aksesDeveloper = new AccessPermissions(roleDeveloper);
        aksesDeveloper.setAksesTambahRisiko(true);
        aksesDeveloper.setAksesHapusRisiko(true);
        aksesDeveloper.setAksesRisiko(true);
        aksesDeveloper.setAksesUbahRisiko(true);
        aksesDeveloper.setAksesTabelRisiko(true);

        aksesDeveloper.setAksesRiskRating(true);
        aksesDeveloper.setUbahRiskRating(true);

        aksesDeveloper.setAksesRiskLevel(true);
        aksesDeveloper.setUbahRiskLevel(true);

        aksesDeveloper.setUbahReminder(true);
        accessPermissionDB.save(aksesDeveloper);
        // Akses Developer selesai
        // Inisiasi Akses Selesai


        // Inisiasi Akun Awal
        Employee admin = new Employee(
                "Aku Administrator",
                Employee.Status.AKTIF,
                "administrator",
                "administrator123",
                "0123",
                "administrator@sirio.com",
                "Administrator",
                roleAdministrator
        );
        Employee supervisor = new Employee(
                "Aku Supervisor",
                Employee.Status.AKTIF,
                "supervisor",
                "supervisor123",
                "0123",
                "supervisor@sirio.com",
                "Supervisor",
                roleSupervisor
        );
        Employee manajer = new Employee(
                "Aku Manajer",
                Employee.Status.AKTIF,
                "manajer",
                "manajer123",
                "0123",
                "manajer@sirio.com",
                "QA Manajer",
                roleManajer
        );
        Employee lead = new Employee(
                "Aku Lead",
                Employee.Status.AKTIF,
                "leadlead",
                "leadlead123",
                "0123",
                "lead@sirio.com",
                "QA Lead",
                roleLead
        );
        Employee officer = new Employee(
                "Aku Officer",
                Employee.Status.AKTIF,
                "qaofficer",
                "qaofficer123",
                "0123",
                "officer@sirio.com",
                "QA Officer",
                roleOfficer
        );
        Employee bm = new Employee(
                "Aku BM",
                Employee.Status.AKTIF,
                "branchmanager",
                "branchmanager123",
                "0123",
                "bm@sirio.com",
                "Branch Manager",
                roleBM
        );
        Employee superQA = new Employee(
                "Aku SuperQA",
                Employee.Status.AKTIF,
                "superofficer",
                "superofficer123",
                "0123",
                "superQA@sirio.com",
                "Super QA",
                roleSuper
        );
        Employee dev = new Employee(
                "dev",
                Employee.Status.AKTIF,
                "dev",
                "dev",
                "0123",
                "developers@sirio.com",
                "Core Developers",
                roleDeveloper
        );

        employeeRestService.buatEmployee(admin);
        employeeRestService.buatEmployee(supervisor);
        employeeRestService.buatEmployee(manajer);
        employeeRestService.buatEmployee(lead);
        employeeRestService.buatEmployee(officer);
        employeeRestService.buatEmployee(bm);
        employeeRestService.buatEmployee(superQA);
        employeeRestService.buatEmployee(dev);
        // Inisiasi Akun Awal selesai
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
