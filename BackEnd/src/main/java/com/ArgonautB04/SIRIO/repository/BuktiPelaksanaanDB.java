package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuktiPelaksanaanDB extends JpaRepository<BuktiPelaksanaan, Integer> {
}
