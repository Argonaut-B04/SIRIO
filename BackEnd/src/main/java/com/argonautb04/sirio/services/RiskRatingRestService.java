package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.RiskRating;

import java.util.List;

public interface RiskRatingRestService {
    RiskRating buatRiskRating(RiskRating riskRating);

    RiskRating getById(int idRiskRating);

    List<RiskRating> getAll();

    RiskRating ubahRiskRating(int idRiskRating, RiskRating riskRating);

    void hapusRiskRating(int idRiskRating);

    boolean isExistInDatabase(RiskRating riskRating);

    void clear();
}
