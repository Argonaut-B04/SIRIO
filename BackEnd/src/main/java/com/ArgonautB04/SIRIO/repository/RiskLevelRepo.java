package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskLevelRepo extends JpaRepository<RiskLevel, Integer> {
}
