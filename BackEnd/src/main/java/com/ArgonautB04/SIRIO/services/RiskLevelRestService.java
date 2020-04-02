package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskLevel;

import java.util.List;

public interface RiskLevelRestService {
    RiskLevel buatRiskLevel(RiskLevel riskLevel);

    RiskLevel getById(int idRiskLevel);

    List<RiskLevel> getAll();

    List<RiskLevel> getAktif();

    RiskLevel ubahRiskLevel(int idRiskLevel, RiskLevel riskLevel);

    void hapusRiskLevel(int idRiskLevel);

    boolean isExistInDatabase(RiskLevel riskLevel);

    void nonaktifkan(RiskLevel riskLevel);
}
