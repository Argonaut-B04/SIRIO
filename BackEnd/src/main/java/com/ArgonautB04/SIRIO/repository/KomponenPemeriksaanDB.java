package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KomponenPemeriksaanDB extends JpaRepository<KomponenPemeriksaan, Integer> {
    List<KomponenPemeriksaan> findAllByHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);
}
