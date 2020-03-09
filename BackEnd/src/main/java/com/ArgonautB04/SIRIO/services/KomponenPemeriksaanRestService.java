package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;

import java.util.List;

public interface KomponenPemeriksaanRestService {
    KomponenPemeriksaan createKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    KomponenPemeriksaan getById(int id_komponen_pemeriksaan);

    List<KomponenPemeriksaan> getAll();

    KomponenPemeriksaan updateKomponenPemeriksaan(int id_komponen_pemeriksaan, KomponenPemeriksaan komponenPemeriksaan);

    void deleteKomponenPemeriksaan(int id_komponen_pemeriksaan);
}
