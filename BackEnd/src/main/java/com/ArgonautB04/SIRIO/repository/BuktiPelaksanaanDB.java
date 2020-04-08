package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BuktiPelaksanaanDB extends JpaRepository<BuktiPelaksanaan, Integer> {
    List<BuktiPelaksanaan> findAllByPembuat(Employee pembuat);

    List<BuktiPelaksanaan> findAllByRekomendasiIn(Collection<Rekomendasi> rekomendasi);
}
