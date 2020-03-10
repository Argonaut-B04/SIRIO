package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskLevel;

import java.util.List;

public interface RiskLevelRestService {
    RiskLevel createRiskLevel(RiskLevel riskLevel);

    RiskLevel getById(int idRiskLevel);

    List<RiskLevel> getAll();

    RiskLevel updateRiskLevel(int idRiskLevel, RiskLevel riskLevel);

    void deleteRiskLevel(int idRiskLevel);
}
