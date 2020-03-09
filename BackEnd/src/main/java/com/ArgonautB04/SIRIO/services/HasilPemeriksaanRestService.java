package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;

import java.util.List;

public interface HasilPemeriksaanRestService {
    HasilPemeriksaan createHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan getById(int id_hasil_pemeriksaan);

    List<HasilPemeriksaan> getAll();

    HasilPemeriksaan updateHasilPemeriksaan(int id_hasil_pemeriksaan, HasilPemeriksaan hasilPemeriksaan);

    void deleteHasilPemeriksaan(int id_hasil_pemeriksaan);
}
