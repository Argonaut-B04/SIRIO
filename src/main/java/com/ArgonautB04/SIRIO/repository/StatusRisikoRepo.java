package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.StatusRisiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRisikoRepo extends JpaRepository<StatusRisiko, Integer> {
}