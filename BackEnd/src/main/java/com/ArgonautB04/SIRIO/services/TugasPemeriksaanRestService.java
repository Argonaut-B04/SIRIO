package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.util.List;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan buatTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getAll();

    List<TugasPemeriksaan> getByPelaksana(Employee pelaksana);

    List<TugasPemeriksaan> getByKantorCabang(KantorCabang kantorCabang);

    TugasPemeriksaan ubahTugasPemeriksaan(int idTugasPemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void hapusTugasPemeriksaan(int idTugasPemeriksaan);
}
