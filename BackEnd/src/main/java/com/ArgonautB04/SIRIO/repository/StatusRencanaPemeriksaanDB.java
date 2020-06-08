package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.StatusRencanaPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRencanaPemeriksaanDB extends JpaRepository<StatusRencanaPemeriksaan, Integer> {
}
