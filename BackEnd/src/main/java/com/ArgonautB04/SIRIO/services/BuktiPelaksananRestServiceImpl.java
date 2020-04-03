package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.repository.BuktiPelaksanaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BuktiPelaksananRestServiceImpl implements BuktiPelaksanaanRestService {

    @Autowired
    private BuktiPelaksanaanDB buktiPelaksanaanDB;

    @Override
    public BuktiPelaksanaan buatBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan) {
        return buktiPelaksanaanDB.save(buktiPelaksanaan);
    }

    @Override
    public BuktiPelaksanaan getById(int idBuktiPelaksanaan) {
        Optional<BuktiPelaksanaan> buktiPelaksanaan = buktiPelaksanaanDB.findById(idBuktiPelaksanaan);
        if (buktiPelaksanaan.isPresent()) return buktiPelaksanaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<BuktiPelaksanaan> getAll() {
        return buktiPelaksanaanDB.findAll();
    }

    @Override
    public BuktiPelaksanaan ubahBuktiPelaksanaan(int idBuktiPelaksanaan, BuktiPelaksanaan buktiPelaksanaan) {
        BuktiPelaksanaan target = getById(idBuktiPelaksanaan);
        target.setFeedback(buktiPelaksanaan.getFeedback());
        target.setKeterangan(buktiPelaksanaan.getKeterangan());
        target.setLampiran(buktiPelaksanaan.getLampiran());
        target.setPembuat(buktiPelaksanaan.getPembuat());
        target.setPemeriksa(buktiPelaksanaan.getPemeriksa());
        target.setRekomendasi(buktiPelaksanaan.getRekomendasi());
        target.setStatusBuktiPelaksanaan(buktiPelaksanaan.getStatusBuktiPelaksanaan());
        return buktiPelaksanaanDB.save(target);
    }

    @Override
    public List<BuktiPelaksanaan> getByPembuat(Employee pembuat) {
        return buktiPelaksanaanDB.findAllByPembuat(pembuat);
    }

    @Override
    public List<BuktiPelaksanaan> getByDaftarRekomendasi(List<Rekomendasi> rekomendasiList) {
        return buktiPelaksanaanDB.findAllByRekomendasiIn(rekomendasiList);
    }
}
