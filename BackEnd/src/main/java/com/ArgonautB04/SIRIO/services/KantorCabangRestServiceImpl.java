package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.repository.KantorCabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class KantorCabangRestServiceImpl implements KantorCabangRestService {

    @Autowired
    private KantorCabangDB kantorCabangDB;

    @Override
    public KantorCabang buatKantorCabang(KantorCabang kantorCabang) {
        return kantorCabangDB.save(kantorCabang);
    }

    @Override
    public KantorCabang getById(int idKantorCabang) {
        Optional<KantorCabang> kantorCabang = kantorCabangDB.findById(idKantorCabang);
        if (kantorCabang.isPresent()) return kantorCabang.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<KantorCabang> getAll() {
        return kantorCabangDB.findAll();
    }

    @Override
    public KantorCabang ubahKantorCabang(int idKantorCabang, KantorCabang kantorCabang) {
        KantorCabang target = getById(idKantorCabang);
        target.setArea(kantorCabang.getArea());
        target.setRiskRating(kantorCabang.getRiskRating());
        target.setRegional(kantorCabang.getRegional());
        target.setPemilik(kantorCabang.getPemilik());
        target.setPembuat(kantorCabang.getPembuat());
        target.setNamaKantor(kantorCabang.getNamaKantor());
        target.setKunjunganAudit(kantorCabang.getKunjunganAudit());
        return kantorCabangDB.save(target);
    }

    @Override
    public void hapusKantorCabang(int idKantorCabang) {
        kantorCabangDB.deleteById(idKantorCabang);
    }
}
