package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskRating;

public interface RiskRatingRestService {
    RiskRating createRiskRating(RiskRating riskRating);

    RiskRating getById(int id_risk_rating);

    RiskRating updateRiskRating(int id_risk_rating, RiskRating riskRating);

    void deleteRiskRating(int id_risk_rating);
}
