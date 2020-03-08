package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.TemuanRisiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemuanRIsikoRepo extends JpaRepository<TemuanRisiko, Integer> {
}
