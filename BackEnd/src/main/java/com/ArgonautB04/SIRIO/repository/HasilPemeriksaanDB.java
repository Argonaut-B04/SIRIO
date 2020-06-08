package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.StatusHasilPemeriksaan;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface HasilPemeriksaanDB extends JpaRepository<HasilPemeriksaan, Integer> {
    List<HasilPemeriksaan> findAllByPembuat(Employee pembuat);

    List<HasilPemeriksaan> findAllByTugasPemeriksaanIn(Collection<TugasPemeriksaan> tugasPemeriksaan);

    List<HasilPemeriksaan> findAllByTugasPemeriksaanInAndStatusHasilPemeriksaan(
            Collection<TugasPemeriksaan> tugasPemeriksaan, StatusHasilPemeriksaan status);

    Optional<HasilPemeriksaan> findByTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan);
}
