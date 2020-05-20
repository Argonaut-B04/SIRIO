package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.time.LocalDate;
import java.util.List;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan buatTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getAll();

    List<TugasPemeriksaan> getByPelaksana(Employee pelaksana);

    List<TugasPemeriksaan> getByKantorCabang(KantorCabang kantorCabang);

    List<TugasPemeriksaan> getByDaftarKantorCabang(List<KantorCabang> kantorCabangList);

    List<TugasPemeriksaan> getByDaftarKantorCabangAndTanggalSelesai(List<KantorCabang> kantorCabangList, LocalDate tanggalMulai, LocalDate tanggalSelesai);

    List<TugasPemeriksaan> getByKantorCabangAndTanggalSelesai(KantorCabang kantorCabang, LocalDate tanggalMulai, LocalDate tanggalSelesai);

    List<TugasPemeriksaan> getByRencana(RencanaPemeriksaan rencanaPemeriksaan);

    boolean isExistInDatabase(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan ubahTugasPemeriksaan(int idTugasPemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void hapusTugasPemeriksaan(int idTugasPemeriksaan);

    TugasPemeriksaan validateExistById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getByDate(LocalDate startDate, LocalDate endDate);
}
