package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TugasPemeriksaanDB extends JpaRepository<TugasPemeriksaan, Integer> {
    List<TugasPemeriksaan> findAllByPelaksana(Employee pelaksana);

    List<TugasPemeriksaan> findAllByKantorCabang(KantorCabang kantorCabang);
}
