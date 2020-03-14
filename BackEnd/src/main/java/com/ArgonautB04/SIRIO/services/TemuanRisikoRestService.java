package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.TemuanRisiko;

import java.util.List;

public interface TemuanRisikoRestService {
    TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko);

    TemuanRisiko getById(int idTemuanRisiko);

    List<TemuanRisiko> getAll();

    List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    TemuanRisiko ubahTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko);

    void hapusTemuanRisiko(int idTemuanRisiko);
}
