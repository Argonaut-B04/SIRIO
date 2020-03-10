package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RekomendasiDB extends JpaRepository<Rekomendasi, Integer> {
}
