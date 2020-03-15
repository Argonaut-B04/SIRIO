package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface HasilPemeriksaanDB extends JpaRepository<HasilPemeriksaan, Integer> {
    List<HasilPemeriksaan> findAllByPembuat(Employee pembuat);

    List<HasilPemeriksaan> findAllByTugasPemeriksaanIn(Collection<TugasPemeriksaan> tugasPemeriksaan);
}
