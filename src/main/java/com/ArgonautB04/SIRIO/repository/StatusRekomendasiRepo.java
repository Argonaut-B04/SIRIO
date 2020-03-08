package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.StatusRekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRekomendasiRepo extends JpaRepository<StatusRekomendasi, Integer> {
}
