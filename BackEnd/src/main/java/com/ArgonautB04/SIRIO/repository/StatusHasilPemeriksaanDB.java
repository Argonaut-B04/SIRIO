package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.StatusHasilPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHasilPemeriksaanDB extends JpaRepository<StatusHasilPemeriksaan, Integer> {
}
