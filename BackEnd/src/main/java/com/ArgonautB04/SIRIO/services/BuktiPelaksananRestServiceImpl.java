package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.repository.BuktiPelaksanaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BuktiPelaksananRestServiceImpl implements BuktiPelaksanaanRestService {

    @Autowired
    private BuktiPelaksanaanRepo bukti_pelaksanaan_repo;

    @Override
    public BuktiPelaksanaan createBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan) {
        return bukti_pelaksanaan_repo.save(buktiPelaksanaan);
    }

    @Override
    public BuktiPelaksanaan getById(int id_bukti_pelaksanaan) {
        Optional<BuktiPelaksanaan> bukti_pelaksanaan = bukti_pelaksanaan_repo.findById(id_bukti_pelaksanaan);
        if (bukti_pelaksanaan.isPresent()) {
            return bukti_pelaksanaan.get();
        } else {
            throw new NoSuchElementException();
        }
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
        return bukti_pelaksanaan_repo.save(target);
    }

    @Override
    public void deleteBuktiPelaksanaan(int id_bukti_pelaksanaan) {
        bukti_pelaksanaan_repo.deleteById(id_bukti_pelaksanaan);
    }
}
