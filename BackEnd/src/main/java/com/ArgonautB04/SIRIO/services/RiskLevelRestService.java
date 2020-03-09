package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskLevel;

public interface RiskLevelRestService {
    RiskLevel createRiskLevel(RiskLevel riskLevel);

    RiskLevel getById(int id_risk_level);

    RiskLevel updateRiskLevel(int id_risk_level, RiskLevel riskLevel);

    void deleteRiskLevel(int id_risk_level);
}
