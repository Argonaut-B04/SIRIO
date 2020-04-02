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
    public RiskLevel buatRiskLevel(RiskLevel riskLevel) {
        riskLevel.setStatus(RiskLevel.Status.AKTIF);
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
    public List<RiskLevel> getAktif() {
        return riskLevelDB.findAllByStatus(RiskLevel.Status.AKTIF);
    }

    @Override
    public RiskLevel ubahRiskLevel(int idRiskLevel, RiskLevel riskLevel) {
        RiskLevel target = getById(idRiskLevel);
        target.setBobotLevel(riskLevel.getBobotLevel());
        target.setNamaLevel(riskLevel.getNamaLevel());
        target.setKeteranganLevel(riskLevel.getKeteranganLevel());
        return riskLevelDB.save(target);
    }

    @Override
    public void hapusRiskLevel(int idRiskLevel) {
        riskLevelDB.deleteById(idRiskLevel);
    }

    @Override
    public boolean isExistInDatabase(RiskLevel riskLevel) {
        return riskLevelDB.findById(riskLevel.getIdLevel()).isPresent();
    }

    @Override
    public void nonaktifkan(RiskLevel riskLevel) {
        RiskLevel target = getById(
                riskLevel.getIdLevel()
        );
        target.setStatus(RiskLevel.Status.NONAKTIF);
    }
}
