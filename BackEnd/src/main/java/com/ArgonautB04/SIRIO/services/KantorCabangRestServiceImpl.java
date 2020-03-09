package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.repository.KantorCabangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class KantorCabangRestServiceImpl implements KantorCabangRestService {

    @Autowired
    private KantorCabangRepo kantorCabangRepo;

    @Override
    public KantorCabang createKantorCabang(KantorCabang kantorCabang) {
        return kantorCabangRepo.save(kantorCabang);
    }

    @Override
    public KantorCabang getById(int id_kantor_cabang) {
        Optional<KantorCabang> kantorCabang = kantorCabangRepo.findById(id_kantor_cabang);
        if (kantorCabang.isPresent()) return kantorCabang.get();
        else throw new NoSuchElementException();
    }

    @Override
    public KantorCabang updateKantorCabang(int id_kantor_cabang, KantorCabang kantorCabang) {
        KantorCabang target = getById(id_kantor_cabang);
        target.setArea(kantorCabang.getArea());
        target.setRisk_rating(kantorCabang.getRisk_rating());
        target.setRegional(kantorCabang.getRegional());
        target.setPemilik(kantorCabang.getPemilik());
        target.setPembuat(kantorCabang.getPembuat());
        target.setNama_kantor(kantorCabang.getNama_kantor());
        target.setJumlah_kunjungan_audit_risk(kantorCabang.getJumlah_kunjungan_audit_risk());
        return kantorCabangRepo.save(target);
    }

    @Override
    public void deleteKantorCabang(int id_kantor_cabang) {
        kantorCabangRepo.deleteById(id_kantor_cabang);
    }
}
