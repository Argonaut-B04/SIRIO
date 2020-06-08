package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KantorCabang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KantorCabangDB extends JpaRepository<KantorCabang, Integer> {

    List<KantorCabang> findAllByStatus(KantorCabang.Status status);

    Optional<KantorCabang> findByIdKantorAndStatus(Integer id, KantorCabang.Status status);

    KantorCabang findAllByPemilik(Employee pemilik);

    List<KantorCabang> findAllByArea(String area);

    List<KantorCabang> findAllByRegional(String regional);

    List<KantorCabang> findAllByAreaAndRegional(String area, String regional);

    Optional<KantorCabang> findByNamaKantor(String nama);

    Boolean existsByNamaKantor(String namaKantor);

}
