package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;

import java.util.List;

public interface KomponenPemeriksaanRestService {
    KomponenPemeriksaan createKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    KomponenPemeriksaan getById(int idKomponenPemeriksaan);

    List<KomponenPemeriksaan> getAll();

    KomponenPemeriksaan updateKomponenPemeriksaan(int idKomponenPemeriksaan, KomponenPemeriksaan komponenPemeriksaan);

    void deleteKomponenPemeriksaan(int idKomponenPemeriksaan);
}
