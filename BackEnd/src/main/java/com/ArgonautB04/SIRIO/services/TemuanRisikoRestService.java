package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.*;

import java.util.List;

public interface TemuanRisikoRestService {
    TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko);

    TemuanRisiko getById(int idTemuanRisiko);

    List<TemuanRisiko> getAll();

//    List<Integer> getAllByMonth();

    List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    List<TemuanRisiko> getHistoriTemuanRisikoKantorCabang(TugasPemeriksaan tugasPemeriksaan, Risiko risiko);

    TemuanRisiko ubahTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko);

    void hapusTemuanRisiko(int idTemuanRisiko);
}
