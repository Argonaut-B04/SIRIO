package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.util.List;
import java.util.Optional;

public interface HasilPemeriksaanRestService {
    HasilPemeriksaan buatHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan getById(int idHasilPemeriksaan);

    List<HasilPemeriksaan> getAll();

    List<HasilPemeriksaan> getByPembuat(Employee pembuat);

    List<HasilPemeriksaan> getByDaftarTugasPemeriksaan(List<TugasPemeriksaan> tugasPemeriksaanList);

    Optional<HasilPemeriksaan> getByTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);

    HasilPemeriksaan buatHasilPemeriksaan(int idHasilPemeriksaan, HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan ubahStatus(int idHasilPemeriksaan, int idStatus);

    void hapusHasilPemeriksaan(int idHasilPemeriksaan);
}
