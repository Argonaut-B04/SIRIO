package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RencanaPemeriksaanRepo extends JpaRepository<RencanaPemeriksaan, Integer> {
}