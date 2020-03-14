package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.TemuanRisiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemuanRisikoDB extends JpaRepository<TemuanRisiko, Integer> {
    List<TemuanRisiko> findAllByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);
}
