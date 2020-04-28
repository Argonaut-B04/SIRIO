package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.repository.*;
import com.ArgonautB04.SIRIO.scheduled.MailScheduler;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimeZone;

@Component
public class DatabaseLoader implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

    private final StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB;
    private final StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;
    private final StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB;
    private final StatusRekomendasiDB statusRekomendasiDB;
    private final EmployeeRestService employeeRestService;
    private final RoleDB roleDB;
    private final AccessPermissionDB accessPermissionDB;
    private final ReminderTemplateDB reminderTemplateDB;

    public DatabaseLoader(StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB, StatusHasilPemeriksaanDB statusHasilPemeriksaanDB, StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB, StatusRekomendasiDB statusRekomendasiDB, EmployeeRestService employeeRestService, RoleDB roleDB, AccessPermissionDB accessPermissionDB, ReminderTemplateDB reminderTemplateDB) {
        this.statusBuktiPelaksanaanDB = statusBuktiPelaksanaanDB;
        this.statusHasilPemeriksaanDB = statusHasilPemeriksaanDB;
        this.statusRencanaPemeriksaanDB = statusRencanaPemeriksaanDB;
        this.statusRekomendasiDB = statusRekomendasiDB;
        this.employeeRestService = employeeRestService;
        this.roleDB = roleDB;
        this.accessPermissionDB = accessPermissionDB;
        this.reminderTemplateDB = reminderTemplateDB;
    }

    @Override
    public void run(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        logger.info("Timezone set to Asia/Jakarta");

        if (statusBuktiPelaksanaanDB.findAll().isEmpty()) populasiStatusBuktiPelaksanaan();
        if (statusRencanaPemeriksaanDB.findAll().isEmpty()) populasiStatusRencanaPemeriksaan();
        if (statusHasilPemeriksaanDB.findAll().isEmpty()) populasiStatusHasilPemeriksaan();
        if (statusRekomendasiDB.findAll().isEmpty()) populasiStatusRekomendasi();
        if (employeeRestService.getAll().isEmpty() && roleDB.findAll().isEmpty() && accessPermissionDB.findAll().isEmpty())
            populasiEmployeedanRole();
        if (reminderTemplateDB.findAll().isEmpty()) populasiReminderTemplate();
        MailScheduler.startService = true;
    }

    private void populasiReminderTemplate() {
        ReminderTemplate reminderTemplateGlobal = new ReminderTemplate();
        reminderTemplateGlobal.setSubjects("Final test");
        reminderTemplateGlobal.setBody("An global template for your email");
        reminderTemplateGlobal.setGlobal(true);
        reminderTemplateDB.save(reminderTemplateGlobal);
        logger.info("Generated Reminder Template: 1");
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
        logger.info("Generated Role: 7");
        // Inisiasi Role Selesai

        // Inisiasi Akses
        // Akses Admin
        AccessPermissions aksesAdmin = new AccessPermissions(roleAdministrator);
        aksesAdmin.setAksesRiskRating(true);
        aksesAdmin.setAksesRiskLevel(true);

        roleAdministrator.setAccessPermissions(aksesAdmin);
        roleDB.save(roleAdministrator);
        // Akses Admin Selesai

        // Akses Supervisor
        AccessPermissions aksesSupervisor = new AccessPermissions(roleSupervisor);
        aksesSupervisor.setAksesRisiko(true);
        aksesSupervisor.setAksesTabelRisiko(true);
        aksesSupervisor.setAksesUbahHierarki(true);

        aksesSupervisor.setAksesRiskRating(true);
        aksesSupervisor.setUbahRiskRating(true);

        aksesSupervisor.setAksesRiskLevel(true);
        aksesSupervisor.setUbahRiskLevel(true);

        aksesSupervisor.setAksesTambahBuktiPelaksanaan(true);
        aksesSupervisor.setAksesBuktiPelaksanaan(true);
        aksesSupervisor.setAksesPersetujuanBuktiPelaksanaan(true);
        roleSupervisor.setAccessPermissions(aksesSupervisor);
        roleDB.save(roleSupervisor);
        // Akses Supervisor Selesai

        // Akses Manajer
        AccessPermissions aksesManajer = new AccessPermissions(roleManajer);
        aksesManajer.setAksesRisiko(true);
        aksesManajer.setAksesTabelRisiko(true);
        aksesManajer.setAksesUbahHierarki(true);

        aksesManajer.setAksesRiskRating(true);
        aksesManajer.setUbahRiskRating(true);

        aksesManajer.setAksesRiskLevel(true);
        aksesManajer.setUbahRiskLevel(true);

        aksesManajer.setAksesTabelRekomendasi(true);
        aksesManajer.setUbahReminder(true);

        aksesManajer.setAksesBuktiPelaksanaan(true);
        aksesManajer.setAksesPersetujuanBuktiPelaksanaan(true);
        roleManajer.setAccessPermissions(aksesManajer);
        roleDB.save(roleManajer);
        // Akses Manajer Selesai

        // Akses Lead
        AccessPermissions aksesLead = new AccessPermissions(roleLead);
        aksesLead.setAksesTambahRisiko(true);
        aksesLead.setAksesHapusRisiko(true);
        aksesLead.setAksesRisiko(true);
        aksesLead.setAksesUbahRisiko(true);
        aksesLead.setAksesTabelRisiko(true);
        aksesLead.setAksesUbahHierarki(true);

        aksesLead.setAksesRiskRating(true);
        aksesLead.setUbahRiskRating(true);

        aksesLead.setAksesRiskLevel(true);
        aksesLead.setUbahRiskLevel(true);

        aksesLead.setAksesTabelRekomendasi(true);
        aksesLead.setUbahReminder(true);

        aksesLead.setAksesTambahBuktiPelaksanaan(true);
        aksesLead.setAksesBuktiPelaksanaan(true);
        aksesLead.setAksesPersetujuanBuktiPelaksanaan(true);
        aksesLead.setAksesUbahBuktiPelaksanaan(true);
        roleLead.setAccessPermissions(aksesLead);
        roleDB.save(roleLead);
        // Akses Lead Selesai

        // Akses Officer
        AccessPermissions aksesOfficer = new AccessPermissions(roleOfficer);
        aksesOfficer.setAksesTambahRisiko(true);
        aksesOfficer.setAksesHapusRisiko(true);
        aksesOfficer.setAksesRisiko(true);
        aksesOfficer.setAksesUbahRisiko(true);
        aksesOfficer.setAksesTabelRisiko(true);

        aksesOfficer.setAksesRiskRating(true);

        aksesOfficer.setAksesRiskLevel(true);

        aksesOfficer.setAksesTabelRekomendasi(true);
        aksesOfficer.setAturTenggatWaktu(true);
        aksesOfficer.setUbahReminder(true);

        aksesOfficer.setAksesTambahBuktiPelaksanaan(true);
        aksesOfficer.setAksesBuktiPelaksanaan(true);
        aksesOfficer.setAksesPersetujuanBuktiPelaksanaan(true);
        aksesOfficer.setAksesUbahBuktiPelaksanaan(true);
        roleOfficer.setAccessPermissions(aksesOfficer);
        roleDB.save(roleOfficer);
        // Akses Officer Selesai

        // Akses BM
        AccessPermissions aksesBM = new AccessPermissions(roleBM);
        aksesBM.setAksesTambahBuktiPelaksanaan(true);
        aksesBM.setAksesBuktiPelaksanaan(true);
        aksesBM.setAksesUbahBuktiPelaksanaan(true);
        aksesBM.setAksesTabelRekomendasi(true);
        roleBM.setAccessPermissions(aksesBM);
        roleDB.save(roleBM);
        // Akses BM Selesai

        // Akses SuperQA
        AccessPermissions aksesSuper = new AccessPermissions(roleSuper, true);
        roleSuper.setAccessPermissions(aksesSuper);
        roleDB.save(roleSuper);

        logger.info("Generated Access Permission: 7");


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

        employeeRestService.buatEmployee(admin);
        employeeRestService.buatEmployee(supervisor);
        employeeRestService.buatEmployee(manajer);
        employeeRestService.buatEmployee(lead);
        employeeRestService.buatEmployee(officer);
        employeeRestService.buatEmployee(bm);
        employeeRestService.buatEmployee(superQA);
        logger.info("Generated Employee: 7");
        // Inisiasi Akun Awal selesai
    }

    private void populasiStatusBuktiPelaksanaan() {
        List<StatusBuktiPelaksanaan> daftarStatusBaru = List.of(
                new StatusBuktiPelaksanaan("Menunggu Persetujuan"),
                new StatusBuktiPelaksanaan("Disetujui"),
                new StatusBuktiPelaksanaan("Ditolak")
        );
        daftarStatusBaru = statusBuktiPelaksanaanDB.saveAll(daftarStatusBaru);

        logger.info("Generated Status Bukti Pelaksanaan: " + daftarStatusBaru.size());
    }

    private void populasiStatusRencanaPemeriksaan() {
        List<StatusRencanaPemeriksaan> daftarStatusBaru = List.of(
                new StatusRencanaPemeriksaan("Draft"),
                new StatusRencanaPemeriksaan("Sedang Dijalankan"),
                new StatusRencanaPemeriksaan("Selesai")
        );
        daftarStatusBaru = statusRencanaPemeriksaanDB.saveAll(daftarStatusBaru);

        logger.info("Generated Status Rencana Pemeriksaan: " + daftarStatusBaru.size());
    }

    private void populasiStatusHasilPemeriksaan() {
        List<StatusHasilPemeriksaan> daftarStatusBaru = List.of(
                new StatusHasilPemeriksaan("Menunggu Persetujuan"),
                new StatusHasilPemeriksaan("Draft"),
                new StatusHasilPemeriksaan("Ditolak"),
                new StatusHasilPemeriksaan("Menunggu Pelaksanaan"),
                new StatusHasilPemeriksaan("Selesai")
        );
        daftarStatusBaru = statusHasilPemeriksaanDB.saveAll(daftarStatusBaru);

        logger.info("Generated Status Hasil Pemeriksaan: " + daftarStatusBaru.size());
    }

    private void populasiStatusRekomendasi() {
        List<StatusRekomendasi> daftarStatusBaru = List.of(
                new StatusRekomendasi("Draft", false),
                new StatusRekomendasi("Menunggu Persetujuan", false),
                new StatusRekomendasi("Ditolak", false),
                new StatusRekomendasi("Menunggu Pengaturan Tenggat Waktu", true),
                new StatusRekomendasi("Menunggu Pelaksanaan", true),
                new StatusRekomendasi("Sedang Dilaksanakan", false),
                new StatusRekomendasi("Selesai", false)
        );

        daftarStatusBaru = statusRekomendasiDB.saveAll(daftarStatusBaru);

        logger.info("Generated Status Rekomendasi: " + daftarStatusBaru.size());
    }
}
