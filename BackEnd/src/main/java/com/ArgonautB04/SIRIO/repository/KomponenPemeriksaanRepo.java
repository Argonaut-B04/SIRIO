package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KomponenPemeriksaanRepo extends JpaRepository<KomponenPemeriksaan, Integer> {
}
