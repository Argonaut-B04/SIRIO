package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskRating;

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
