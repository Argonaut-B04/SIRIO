package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasilPemeriksaanRepo extends JpaRepository<HasilPemeriksaan, Integer> {
}
