package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.KantorCabang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KantorCabangRepo extends JpaRepository<KantorCabang, Integer> {
}
