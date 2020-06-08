package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Risiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RisikoDB extends JpaRepository<Risiko, Integer> {
    List<Risiko> findAllByRisikoKategori(Integer kategori);

    List<Risiko> findAllByRisikoKategoriAndStatus(Integer kategori, Risiko.Status status);

    List<Risiko> findAllByStatus(Risiko.Status status);

    Optional<Risiko> findByNamaRisikoAndStatus(String nama, Risiko.Status status);

}
