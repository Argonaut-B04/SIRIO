package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.RencanaPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RencanaPemeriksaanDB extends JpaRepository<RencanaPemeriksaan, Integer> {
    List<RencanaPemeriksaan> findAllByPembuat(Employee pembuat);

    Optional<RencanaPemeriksaan> findByNamaRencana(String nama);
}
