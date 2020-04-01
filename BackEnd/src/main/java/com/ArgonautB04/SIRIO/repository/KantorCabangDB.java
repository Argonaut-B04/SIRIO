package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KantorCabangDB extends JpaRepository<KantorCabang, Integer> {
    KantorCabang findKantorCabangByPemilik(Employee pemilik);
    List<KantorCabang> findAllByPembuat(Employee pembuat);
}
