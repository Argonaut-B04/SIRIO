package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.TugasPemeriksaan;

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

    HasilPemeriksaan validateExistById(int idHasilPemeriksaan);
}
