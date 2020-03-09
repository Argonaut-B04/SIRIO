package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan createTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int id_tugas_pemeriksaan);

    TugasPemeriksaan updateTugasPemeriksaan(int id_tugas_pemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void deleteTugasPemeriksaan(int id_tugas_pemeriksaan);
}
