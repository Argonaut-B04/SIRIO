package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
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
        kantorCabang.setStatus(KantorCabang.Status.AKTIF);
        return kantorCabangDB.save(kantorCabang);
    }

    @Override
    public KantorCabang getById(int idKantorCabang) {
        Optional<KantorCabang> kantorCabang = kantorCabangDB.findByIdKantorAndStatus(idKantorCabang, KantorCabang.Status.AKTIF);
        if (kantorCabang.isPresent()) return kantorCabang.get();
        else throw new NoSuchElementException();
    }

    @Override
    public KantorCabang getByPemilik(Employee pemilik) {
        return kantorCabangDB.findAllByPemilik(pemilik);
    }

    @Override
    public List<KantorCabang> getAll() {
        return kantorCabangDB.findAllByStatus(KantorCabang.Status.AKTIF);
    }

    @Override
    public KantorCabang getByNama(String nama){ return kantorCabangDB.findByNamaKantor(nama);}

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

    @Override
    public KantorCabang nonaktifkanKantor(int idKantor) {
        KantorCabang kantorCabang = getById(idKantor);
        kantorCabang.setStatus(KantorCabang.Status.NONAKTIF);
        return kantorCabang;
    }

    @Override
    public KantorCabang aktifkanKantor(int idKantor) {
        KantorCabang kantorCabang= getById(idKantor);
        kantorCabang.setStatus(KantorCabang.Status.AKTIF);
        return kantorCabang;
    }

//    @Override
//    public List<KantorCabang> getByPembuat(Employee pembuat){
//        return kantorCabangDB.findAllByPembuat(pembuat);
//    }
}
