package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RekomendasiDB extends JpaRepository<Rekomendasi, Integer> {
    List<Rekomendasi> findAllByPembuat(Employee pembuat);
}
