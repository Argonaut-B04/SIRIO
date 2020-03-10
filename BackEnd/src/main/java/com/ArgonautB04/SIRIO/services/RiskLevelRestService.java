package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskLevel;

import java.util.List;

public interface RiskLevelRestService {
    RiskLevel buatRiskLevel(RiskLevel riskLevel);

    RiskLevel getById(int idRiskLevel);

    List<RiskLevel> getAll();

    RiskLevel ubahRiskLevel(int idRiskLevel, RiskLevel riskLevel);

    void hapusRiskLevel(int idRiskLevel);
}
