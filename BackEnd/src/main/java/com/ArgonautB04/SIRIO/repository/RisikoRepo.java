package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Risiko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RisikoRepo extends JpaRepository<Risiko, Integer> {
}
