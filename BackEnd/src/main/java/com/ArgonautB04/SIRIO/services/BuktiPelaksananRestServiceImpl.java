package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.repository.BuktiPelaksanaanRepo;
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
    private BuktiPelaksanaanRepo buktiPelaksanaanRepo;

    @Override
    public BuktiPelaksanaan createBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan) {
        return buktiPelaksanaanRepo.save(buktiPelaksanaan);
    }

    @Override
    public BuktiPelaksanaan getById(int id_bukti_pelaksanaan) {
        Optional<BuktiPelaksanaan> buktiPelaksanaan = buktiPelaksanaanRepo.findById(id_bukti_pelaksanaan);
        if (buktiPelaksanaan.isPresent()) return buktiPelaksanaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<BuktiPelaksanaan> getAll() {
        return buktiPelaksanaanRepo.findAll();
    }

    @Override
    public BuktiPelaksanaan updateBuktiPelaksanaan(int id_bukti_pelaksanaan, BuktiPelaksanaan buktiPelaksanaan) {
        BuktiPelaksanaan target = getById(id_bukti_pelaksanaan);
        target.setFeedback(buktiPelaksanaan.getFeedback());
        target.setKeterangan(buktiPelaksanaan.getKeterangan());
        target.setLampiran(buktiPelaksanaan.getLampiran());
        target.setPembuat(buktiPelaksanaan.getPembuat());
        target.setPemeriksa(buktiPelaksanaan.getPemeriksa());
        target.setRekomendasi(buktiPelaksanaan.getRekomendasi());
        target.setStatus_bukti_pelaksanaan(buktiPelaksanaan.getStatus_bukti_pelaksanaan());
        return buktiPelaksanaanRepo.save(target);
    }

    @Override
    public void deleteBuktiPelaksanaan(int id_bukti_pelaksanaan) {
        buktiPelaksanaanRepo.deleteById(id_bukti_pelaksanaan);
    }
}
