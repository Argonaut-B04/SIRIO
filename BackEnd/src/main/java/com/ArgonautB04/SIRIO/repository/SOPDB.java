package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.SOP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SOPDB extends JpaRepository<SOP, Integer> {

    List<SOP> findAllByStatus(SOP.Status status);
    Optional<SOP> findByJudul(String judul);
}
