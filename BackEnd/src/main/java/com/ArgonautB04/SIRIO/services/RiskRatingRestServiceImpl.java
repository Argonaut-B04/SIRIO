package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskRating;
import com.ArgonautB04.SIRIO.repository.RiskRatingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RiskRatingRestServiceImpl implements RiskRatingRestService {

    @Autowired
    private RiskRatingDB riskRatingDB;

    @Override
    public RiskRating buatRiskRating(RiskRating riskRating) {
        return riskRatingDB.save(riskRating);
    }

    @Override
    public RiskRating getById(int idRiskRating) {
        Optional<RiskRating> riskRating = riskRatingDB.findById(idRiskRating);
        if (riskRating.isPresent()) return riskRating.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<RiskRating> getAll() {
        return riskRatingDB.findAll();
    }

    @Override
    public RiskRating ubahRiskRating(int idRiskRating, RiskRating riskRating) {
        RiskRating target = getById(idRiskRating);
        target.setSkorMinimal(riskRating.getSkorMinimal());
        target.setSkorMaksimal(riskRating.getSkorMaksimal());
        target.setPengelola(riskRating.getPengelola());
        target.setNamaRating(riskRating.getNamaRating());
        target.setKeteranganRating(riskRating.getKeteranganRating());
        return riskRatingDB.save(target);
    }

    @Override
    public void hapusRiskRating(int idRiskRating) {
        riskRatingDB.deleteById(idRiskRating);
    }

    @Override
    public boolean isExistInDatabase(RiskRating riskRating) {
        return riskRatingDB.existsById(riskRating.getIdRating());
    }
}
