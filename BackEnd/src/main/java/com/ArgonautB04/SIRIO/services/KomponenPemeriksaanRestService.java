package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Risiko;

import java.util.List;

public interface KomponenPemeriksaanRestService {
    KomponenPemeriksaan buatKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    KomponenPemeriksaan getById(int idKomponenPemeriksaan);

    List<KomponenPemeriksaan> getAll();

    List<KomponenPemeriksaan> getByHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    List<KomponenPemeriksaan> getByDaftarHasilPemeriksaan(List<HasilPemeriksaan> hasilPemeriksaanList);

    KomponenPemeriksaan ubahKomponenPemeriksaan(int idKomponenPemeriksaan, KomponenPemeriksaan komponenPemeriksaan);

    void hapusKomponenPemeriksaan(int idKomponenPemeriksaan);

    KomponenPemeriksaan getByRisiko(Risiko risiko);
}
