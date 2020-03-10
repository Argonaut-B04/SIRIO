package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;

import java.util.List;

public interface KomponenPemeriksaanRestService {
    KomponenPemeriksaan buatKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    KomponenPemeriksaan getById(int idKomponenPemeriksaan);

    List<KomponenPemeriksaan> getAll();

    KomponenPemeriksaan ubahKomponenPemeriksaan(int idKomponenPemeriksaan, KomponenPemeriksaan komponenPemeriksaan);

    void hapusKomponenPemeriksaan(int idKomponenPemeriksaan);
}
