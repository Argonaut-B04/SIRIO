package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.model.RiskLevel.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface RiskLevelDB extends JpaRepository<RiskLevel, Integer> {

    List<RiskLevel> findAllByStatus(@NotNull Status status);
}
