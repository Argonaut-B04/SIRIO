package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Risiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KomponenPemeriksaanDB extends JpaRepository<KomponenPemeriksaan, Integer> {
    List<KomponenPemeriksaan> findAllByHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan);

    List<KomponenPemeriksaan> findAllByHasilPemeriksaanIn(Collection<HasilPemeriksaan> hasilPemeriksaan);

    List<KomponenPemeriksaan> findAllByHasilPemeriksaanInAndRisiko(Collection<HasilPemeriksaan> hasilPemeriksaan,
                                                                   Risiko risiko);

    KomponenPemeriksaan findAllByRisiko(Risiko risiko);
}
