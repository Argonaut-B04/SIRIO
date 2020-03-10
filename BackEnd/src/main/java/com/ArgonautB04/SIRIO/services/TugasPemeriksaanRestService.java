package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.util.List;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan buatTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getAll();

    TugasPemeriksaan ubahTugasPemeriksaan(int idTugasPemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void hapusTugasPemeriksaan(int idTugasPemeriksaan);
}
