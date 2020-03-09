package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.util.List;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan createTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int id_tugas_pemeriksaan);

    List<TugasPemeriksaan> getAll();

    TugasPemeriksaan updateTugasPemeriksaan(int id_tugas_pemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void deleteTugasPemeriksaan(int id_tugas_pemeriksaan);
}
