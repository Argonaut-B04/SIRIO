package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.StatusRencanaPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRencanaPemeriksaanRepo extends JpaRepository<StatusRencanaPemeriksaan, Integer> {
}
