package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.util.List;

public interface HasilPemeriksaanRestService {
    HasilPemeriksaan buatHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    HasilPemeriksaan getById(int idHasilPemeriksaan);

    List<HasilPemeriksaan> getAll();

    List<HasilPemeriksaan> getByPembuat(Employee pembuat);

    List<HasilPemeriksaan> getByDaftarTugasPemeriksaan(List<TugasPemeriksaan> tugasPemeriksaanList);

    HasilPemeriksaan buatHasilPemeriksaan(int idHasilPemeriksaan, HasilPemeriksaan hasilPemeriksaan);

    void hapusHasilPemeriksaan(int idHasilPemeriksaan);
}
