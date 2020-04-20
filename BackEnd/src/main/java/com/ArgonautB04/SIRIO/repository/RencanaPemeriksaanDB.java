package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RencanaPemeriksaanDB extends JpaRepository<RencanaPemeriksaan, Integer> {
     List<RencanaPemeriksaan> findAllByPembuat(Employee pembuat);

     Optional<RencanaPemeriksaan> findByNamaRencana(String nama);
}
