package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KantorCabang;
import com.argonautb04.sirio.model.RencanaPemeriksaan;
import com.argonautb04.sirio.model.TugasPemeriksaan;

import java.time.LocalDate;
import java.util.List;

public interface TugasPemeriksaanRestService {
    TugasPemeriksaan buatTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan getById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getAll();

    List<TugasPemeriksaan> getByPelaksana(Employee pelaksana);

    List<TugasPemeriksaan> getByKantorCabang(KantorCabang kantorCabang);

    List<TugasPemeriksaan> getByDaftarKantorCabang(List<KantorCabang> kantorCabangList);

    List<TugasPemeriksaan> getByDaftarKantorCabangAndTanggalSelesai(List<KantorCabang> kantorCabangList,
                                                                    LocalDate tanggalMulai, LocalDate tanggalSelesai);

    List<TugasPemeriksaan> getByKantorCabangAndTanggalSelesai(KantorCabang kantorCabang, LocalDate tanggalMulai,
                                                              LocalDate tanggalSelesai);

    List<TugasPemeriksaan> getByRencana(RencanaPemeriksaan rencanaPemeriksaan);

    boolean isExistInDatabase(TugasPemeriksaan tugasPemeriksaan);

    TugasPemeriksaan ubahTugasPemeriksaan(int idTugasPemeriksaan, TugasPemeriksaan tugasPemeriksaan);

    void hapusTugasPemeriksaan(int idTugasPemeriksaan);

    TugasPemeriksaan validateExistById(int idTugasPemeriksaan);

    List<TugasPemeriksaan> getByDate(LocalDate startDate, LocalDate endDate);
}
