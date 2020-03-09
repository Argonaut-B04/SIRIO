package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskRating;

import java.util.List;

public interface RiskRatingRestService {
    RiskRating createRiskRating(RiskRating riskRating);

    RiskRating getById(int id_risk_rating);

    List<RiskRating> getAll();

    RiskRating updateRiskRating(int id_risk_rating, RiskRating riskRating);

    void deleteRiskRating(int id_risk_rating);
}
