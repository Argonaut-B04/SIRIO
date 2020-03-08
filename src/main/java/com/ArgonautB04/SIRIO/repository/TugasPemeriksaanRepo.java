package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TugasPemeriksaanRepo extends JpaRepository<TugasPemeriksaan, Integer> {
}
