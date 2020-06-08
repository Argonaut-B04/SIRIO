package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.RiskRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRatingDB extends JpaRepository<RiskRating, Integer> {
}
