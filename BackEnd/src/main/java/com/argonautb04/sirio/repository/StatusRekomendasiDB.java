package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.StatusRekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Repository
public interface StatusRekomendasiDB extends JpaRepository<StatusRekomendasi, Integer> {
    Optional<StatusRekomendasi> findByNamaStatus(@NotNull @Size(max = 50) String namaStatus);
}
