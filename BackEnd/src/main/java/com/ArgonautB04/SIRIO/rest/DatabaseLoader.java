package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.StatusBuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.StatusHasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.StatusRekomendasi;
import com.ArgonautB04.SIRIO.model.StatusRencanaPemeriksaan;
import com.ArgonautB04.SIRIO.repository.StatusBuktiPelaksanaanDB;
import com.ArgonautB04.SIRIO.repository.StatusHasilPemeriksaanDB;
import com.ArgonautB04.SIRIO.repository.StatusRekomendasiDB;
import com.ArgonautB04.SIRIO.repository.StatusRencanaPemeriksaanDB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB;
    private final StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;
    private final StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB;
    private final StatusRekomendasiDB statusRekomendasiDB;

    public DatabaseLoader(StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB, StatusHasilPemeriksaanDB statusHasilPemeriksaanDB, StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB, StatusRekomendasiDB statusRekomendasiDB) {
        this.statusBuktiPelaksanaanDB = statusBuktiPelaksanaanDB;
        this.statusHasilPemeriksaanDB = statusHasilPemeriksaanDB;
        this.statusRencanaPemeriksaanDB = statusRencanaPemeriksaanDB;
        this.statusRekomendasiDB = statusRekomendasiDB;
    }

    @Override
    public void run(String... args) throws Exception {
        if (statusBuktiPelaksanaanDB.findAll().isEmpty()) populasiStatusBuktiPelaksanaan();
        if (statusRencanaPemeriksaanDB.findAll().isEmpty()) populasiStatusRencanaPemeriksaan();
        if (statusHasilPemeriksaanDB.findAll().isEmpty()) populasiStatusHasilPemeriksaan();
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
