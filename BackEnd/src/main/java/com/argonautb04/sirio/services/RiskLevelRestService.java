package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.RiskLevel;

import java.util.List;

public interface RiskLevelRestService {
    RiskLevel buatRiskLevel(RiskLevel riskLevel);

    RiskLevel getById(int idRiskLevel);

    List<RiskLevel> getAll();

    List<RiskLevel> getAktif();

    RiskLevel ubahRiskLevel(int idRiskLevel, RiskLevel riskLevel);

    boolean isExistInDatabase(RiskLevel riskLevel);

    boolean isExistInDatabase(String namaRiskLevel);

    void nonaktifkan(RiskLevel riskLevel);
}
