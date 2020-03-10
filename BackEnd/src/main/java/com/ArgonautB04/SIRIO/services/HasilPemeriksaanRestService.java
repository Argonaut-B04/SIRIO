package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;

import java.util.List;

public interface HasilPemeriksaanRestService {
    HasilPemeriksaan createHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan getById(int idHasilPemeriksaan);

    List<HasilPemeriksaan> getAll();

    HasilPemeriksaan updateHasilPemeriksaan(int idHasilPemeriksaan, HasilPemeriksaan hasilPemeriksaan);

    void deleteHasilPemeriksaan(int idHasilPemeriksaan);
}
