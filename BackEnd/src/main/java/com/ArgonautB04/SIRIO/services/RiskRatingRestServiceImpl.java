package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskRating;
import com.ArgonautB04.SIRIO.repository.RiskRatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RiskRatingRestServiceImpl implements RiskRatingRestService {

    @Autowired
    private RiskRatingRepo riskRatingRepo;

    @Override
    public RiskRating createRiskRating(RiskRating riskRating) {
        return riskRatingRepo.save(riskRating);
    }

    @Override
    public RiskRating getById(int id_risk_rating) {
        Optional<RiskRating> riskRating = riskRatingRepo.findById(id_risk_rating);
        if (riskRating.isPresent()) return riskRating.get();
        else throw new NoSuchElementException();
    }

    @Override
    public RiskRating updateRiskRating(int id_risk_rating, RiskRating riskRating) {
        RiskRating target = getById(id_risk_rating);
        target.setSkor_minimal(riskRating.getSkor_minimal());
        target.setSkor_maksimal(riskRating.getSkor_maksimal());
        target.setPengelola(riskRating.getPengelola());
        target.setNama_rating(riskRating.getNama_rating());
        target.setKeterangan_rating(riskRating.getKeterangan_rating());
        return riskRatingRepo.save(target);
    }

    @Override
    public void deleteRiskRating(int id_risk_rating) {
        riskRatingRepo.deleteById(id_risk_rating);
    }
}
