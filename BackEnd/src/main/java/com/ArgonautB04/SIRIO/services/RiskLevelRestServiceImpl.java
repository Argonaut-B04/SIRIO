package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.repository.RiskLevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RiskLevelRestServiceImpl implements RiskLevelRestService {

    @Autowired
    private RiskLevelRepo riskLevelRepo;

    @Override
    public RiskLevel createRiskLevel(RiskLevel riskLevel) {
        return riskLevelRepo.save(riskLevel);
    }

    @Override
    public RiskLevel getById(int id_risk_level) {
        Optional<RiskLevel> riskLevel = riskLevelRepo.findById(id_risk_level);
        if (riskLevel.isPresent()) return riskLevel.get();
        else throw new NoSuchElementException();
    }

    @Override
    public RiskLevel updateRiskLevel(int id_risk_level, RiskLevel riskLevel) {
        RiskLevel target = getById(id_risk_level);
        target.setBobot_level(riskLevel.getBobot_level());
        target.setStatus(riskLevel.getStatus());
        target.setPengelola(riskLevel.getPengelola());
        target.setNama_level(riskLevel.getNama_level());
        target.setKeterangan_level(riskLevel.getKeterangan_level());
        return riskLevelRepo.save(target);
    }

    @Override
    public void deleteRiskLevel(int id_risk_level) {
        riskLevelRepo.deleteById(id_risk_level);
    }
}
