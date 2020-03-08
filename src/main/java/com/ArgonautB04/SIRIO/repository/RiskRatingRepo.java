package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.RiskRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRatingRepo extends JpaRepository<RiskRating, Integer> {
}
