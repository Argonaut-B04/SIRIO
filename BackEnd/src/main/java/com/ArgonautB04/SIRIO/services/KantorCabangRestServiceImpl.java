package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.repository.KantorCabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

//    @Override
//    public boolean isExistInDatabase(KantorCabang kantorCabang) {
//        return kantorCabangDB.findById(kantorCabang.getIdKantor()).isPresent();
//    }

    //    @Override
//    public KantorCabang isExistInDatabase(String namaKantor) {
//        return kantorCabangDB.findByNamaKantor(namaKantor);
//    }
//
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
    public List<KantorCabang> getByArea(String area) {
        return kantorCabangDB.findAllByArea(area);
    }

    @Override
    public List<KantorCabang> getByRegional(String regional) {
        return kantorCabangDB.findAllByRegional(regional);
    }

    @Override
    public List<KantorCabang> getByAreaAndRegional(String area, String regional) {
        return kantorCabangDB.findAllByAreaAndRegional(area, regional);
    }

    @Override
    public List<KantorCabang> getAll() {
        return kantorCabangDB.findAllByStatus(KantorCabang.Status.AKTIF);
    }

    @Override
    public Optional<KantorCabang> getByNama(String nama) {
        return kantorCabangDB.findByNamaKantor(nama);
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

    @Override
    public KantorCabang nonaktifkanKantor(int idKantor) {
        KantorCabang kantorCabang = getById(idKantor);
        kantorCabang.setStatus(KantorCabang.Status.NONAKTIF);
        return kantorCabang;
    }

    @Override
    public KantorCabang aktifkanKantor(int idKantor) {
        KantorCabang kantorCabang = getById(idKantor);
        kantorCabang.setStatus(KantorCabang.Status.AKTIF);
        return kantorCabang;
    }

    public KantorCabang validateExistById(int idKantorCabang) {
        Optional<KantorCabang> kantorCabang = kantorCabangDB.findById(idKantorCabang);
        if (kantorCabang.isPresent()) {
            return kantorCabang.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Kantor Cabang dengan ID " + idKantorCabang + " tidak ditemukan!"
            );
        }
    }

    @Override
    public void nullifiedRiskRating() {
        List<KantorCabang> daftarKantorCabang = kantorCabangDB.findAll();
        List<KantorCabang> nullified = new ArrayList<>();
        for (KantorCabang kantorCabang : daftarKantorCabang) {
            kantorCabang.setRiskRating(null);
            nullified.add(kantorCabang);
        }
        kantorCabangDB.saveAll(nullified);
    }

    @Override
    public void recalculateRiskRating() {
//        List<KantorCabang> daftarKantorCabang = kantorCabangDB.findAll();
//        List<KantorCabang> calculated = new ArrayList<>();
//        for (KantorCabang kantorCabang : daftarKantorCabang) {
//             nanti disini akan masukin perhitungan risk rating nya
//             kantorCabang.setRiskRating(null);
//             calculated.add(kantorCabang);
//        }
//        kantorCabangDB.saveAll(calculated);
    }
}
