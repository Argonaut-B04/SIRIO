package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
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
    Optional<KantorCabang> findByNamaKantor(String nama);
    Boolean existsByNamaKantor(String namaKantor);

}
