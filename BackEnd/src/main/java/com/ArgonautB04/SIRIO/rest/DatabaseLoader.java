package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB;
    private final StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;
    private final StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB;
    private final StatusRisikoDB statusRisikoDB;
    private final StatusRekomendasiDB statusRekomendasiDB;

    public DatabaseLoader(StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB, StatusHasilPemeriksaanDB statusHasilPemeriksaanDB, StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB, StatusRisikoDB statusRisikoDB, StatusRekomendasiDB statusRekomendasiDB) {
        this.statusBuktiPelaksanaanDB = statusBuktiPelaksanaanDB;
        this.statusHasilPemeriksaanDB = statusHasilPemeriksaanDB;
        this.statusRencanaPemeriksaanDB = statusRencanaPemeriksaanDB;
        this.statusRisikoDB = statusRisikoDB;
        this.statusRekomendasiDB = statusRekomendasiDB;
    }

    @Override
    public void run(String... args) throws Exception {
        if (statusBuktiPelaksanaanDB.findAll().isEmpty()) populasiStatusBuktiPelaksanaan();
        if (statusRencanaPemeriksaanDB.findAll().isEmpty()) populasiStatusRencanaPemeriksaan();
        if (statusHasilPemeriksaanDB.findAll().isEmpty()) populasiStatusHasilPemeriksaan();
        if (statusRisikoDB.findAll().isEmpty()) populasiStatusRisiko();
        if (statusRekomendasiDB.findAll().isEmpty()) populasiStatusRekomendasi();
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

    private void populasiStatusRisiko() {
        StatusRisiko aktif = new StatusRisiko();
        aktif.setNamaStatus("Aktif");
        statusRisikoDB.save(aktif);

        StatusRisiko tidakAktif = new StatusRisiko();
        tidakAktif.setNamaStatus("Tidak Aktif");
        statusRisikoDB.save(tidakAktif);
    }

    private void populasiStatusRekomendasi() {
        StatusRekomendasi draft = new StatusRekomendasi();
        draft.setNamaStatus("Draft");
        statusRekomendasiDB.save(draft);

        StatusRekomendasi menungguPersetujuan = new StatusRekomendasi();
        menungguPersetujuan.setNamaStatus("Menunggu Persetujuan");
        statusRekomendasiDB.save(menungguPersetujuan);

        StatusRekomendasi ditolak = new StatusRekomendasi();
        ditolak.setNamaStatus("Ditolak");
        statusRekomendasiDB.save(ditolak);

        StatusRekomendasi menungguPengaturanTenggatWaktu = new StatusRekomendasi();
        menungguPengaturanTenggatWaktu.setNamaStatus("Menunggu Pengaturan Tenggat Waktu");
        statusRekomendasiDB.save(menungguPengaturanTenggatWaktu);

        StatusRekomendasi menungguPelaksanaan = new StatusRekomendasi();
        menungguPelaksanaan.setNamaStatus("Menunggu Pelaksanaan");
        statusRekomendasiDB.save(menungguPelaksanaan);

        StatusRekomendasi sedangDilaksanakan = new StatusRekomendasi();
        sedangDilaksanakan.setNamaStatus("Sedang Dilaksanakan");
        statusRekomendasiDB.save(sedangDilaksanakan);

        StatusRekomendasi selesai = new StatusRekomendasi();
        selesai.setNamaStatus("Selesai");
        statusRekomendasiDB.save(selesai);
    }
}
