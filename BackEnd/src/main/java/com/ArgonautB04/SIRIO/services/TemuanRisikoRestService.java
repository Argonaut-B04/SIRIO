package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.*;

import java.time.LocalDate;
import java.util.List;

public interface TemuanRisikoRestService {
    TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko);

    TemuanRisiko getById(int idTemuanRisiko);

    List<TemuanRisiko> getAll();

    List<TemuanRisiko> getAll(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<TemuanRisiko> getByPembuat(int idQa);

    List<TemuanRisiko> getByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Integer> getAllByMonth(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Integer> getByPembuatByMonth(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    List<TemuanRisiko> getHistoriTemuanRisikoKantorCabang(TugasPemeriksaan tugasPemeriksaan, Risiko risiko);

    TemuanRisiko ubahTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko);

    void hapusTemuanRisiko(int idTemuanRisiko);
}
