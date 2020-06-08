package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Risiko;

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
