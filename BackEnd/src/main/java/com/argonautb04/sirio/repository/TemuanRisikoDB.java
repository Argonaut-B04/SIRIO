package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.TemuanRisiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TemuanRisikoDB extends JpaRepository<TemuanRisiko, Integer> {
    List<TemuanRisiko> findAllByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    List<TemuanRisiko> findAllByKomponenPemeriksaanIn(Collection<KomponenPemeriksaan> komponenPemeriksaans);

    List<TemuanRisiko> findAllByPembuat(Employee employee);
}
