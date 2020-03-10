package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.StatusHasilPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHasilPemeriksaanDB extends JpaRepository<StatusHasilPemeriksaan, Integer> {
}
