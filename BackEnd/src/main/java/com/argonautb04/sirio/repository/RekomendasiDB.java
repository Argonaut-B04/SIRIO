package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Rekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface RekomendasiDB extends JpaRepository<Rekomendasi, Integer> {
    List<Rekomendasi> findAllByPembuat(Employee pembuat);

    List<Rekomendasi> findAllByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    List<Rekomendasi> findAllByKomponenPemeriksaanIn(Collection<KomponenPemeriksaan> komponenPemeriksaan);

    List<Rekomendasi> findAllByKomponenPemeriksaanInAndTenggatWaktuBetween(
            Collection<KomponenPemeriksaan> komponenPemeriksaan, LocalDate tenggatWaktu, LocalDate tenggatWaktu2);

    int countAllByTenggatWaktuGreaterThanEqualAndTenggatWaktuLessThan(LocalDate tenggatWaktu, LocalDate tenggatWaktu2);
}