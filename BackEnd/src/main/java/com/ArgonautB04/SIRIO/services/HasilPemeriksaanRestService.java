package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;

public interface HasilPemeriksaanRestService {
    HasilPemeriksaan createHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan getById(int id_hasil_pemeriksaan);

    HasilPemeriksaan updateHasilPemeriksaan(int id_hasil_pemeriksaan, HasilPemeriksaan hasilPemeriksaan);

    void deleteHasilPemeriksaan(int id_hasil_pemeriksaan);
}
