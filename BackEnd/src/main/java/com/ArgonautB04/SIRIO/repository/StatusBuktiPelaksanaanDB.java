package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.StatusBuktiPelaksanaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusBuktiPelaksanaanDB extends JpaRepository<StatusBuktiPelaksanaan, Integer> {
}
