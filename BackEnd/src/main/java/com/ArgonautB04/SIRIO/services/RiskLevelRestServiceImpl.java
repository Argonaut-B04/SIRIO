package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.repository.RiskLevelDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RiskLevelRestServiceImpl implements RiskLevelRestService {

    @Autowired
    private RiskLevelDB riskLevelDB;

    @Override
    public RiskLevel createRiskLevel(RiskLevel riskLevel) {
        return riskLevelDB.save(riskLevel);
    }

    @Override
    public RiskLevel getById(int idRiskLevel) {
        Optional<RiskLevel> riskLevel = riskLevelDB.findById(idRiskLevel);
        if (riskLevel.isPresent()) return riskLevel.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<RiskLevel> getAll() {
        return riskLevelDB.findAll();
    }

    @Override
    public RiskLevel updateRiskLevel(int idRiskLevel, RiskLevel riskLevel) {
        RiskLevel target = getById(idRiskLevel);
        target.setBobotLevel(riskLevel.getBobotLevel());
        target.setStatus(String.valueOf(riskLevel.getStatus()));
        target.setPengelola(riskLevel.getPengelola());
        target.setNamaLevel(riskLevel.getNamaLevel());
        target.setKeteranganLevel(riskLevel.getKeteranganLevel());
        return riskLevelDB.save(target);
    }

    @Override
    public void deleteRiskLevel(int idRiskLevel) {
        riskLevelDB.deleteById(idRiskLevel);
    }
}
