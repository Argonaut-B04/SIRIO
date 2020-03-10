package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskRating;

import java.util.List;

public interface RiskRatingRestService {
    RiskRating createRiskRating(RiskRating riskRating);

    RiskRating getById(int idRiskRating);

    List<RiskRating> getAll();

    RiskRating updateRiskRating(int idRiskRating, RiskRating riskRating);

    void deleteRiskRating(int idRiskRating);
}
