package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.StatusBuktiPelaksanaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusBuktiPelaksanaanDB extends JpaRepository<StatusBuktiPelaksanaan, Integer> {
}
