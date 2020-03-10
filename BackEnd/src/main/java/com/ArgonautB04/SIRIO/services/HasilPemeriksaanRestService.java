package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;

import java.util.List;

public interface HasilPemeriksaanRestService {
    HasilPemeriksaan buatHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan getById(int idHasilPemeriksaan);

    List<HasilPemeriksaan> getAll();

    HasilPemeriksaan buatHasilPemeriksaan(int idHasilPemeriksaan, HasilPemeriksaan hasilPemeriksaan);

    void hapusHasilPemeriksaan(int idHasilPemeriksaan);
}
