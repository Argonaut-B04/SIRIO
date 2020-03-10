package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.util.List;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan createTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getAll();

    TugasPemeriksaan updateTugasPemeriksaan(int idTugasPemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void deleteTugasPemeriksaan(int idTugasPemeriksaan);
}
