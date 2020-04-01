package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Risiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RisikoDB extends JpaRepository<Risiko, Integer> {
    List<Risiko> findAllByRisikoKategori(Integer kategori);
}
