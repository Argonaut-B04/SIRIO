package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.apache.tomcat.jni.Local;
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

    int countAllByTenggatWaktuGreaterThanEqualAndTenggatWaktuLessThan(LocalDate tenggatWaktu, LocalDate tenggatWaktu2);
}