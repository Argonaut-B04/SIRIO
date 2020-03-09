package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.repository.RekomendasiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RekomendasiRestServiceImpl implements RekomendasiRestService {

    @Autowired
    private RekomendasiRepo rekomendasiRepo;

    @Override
    public Rekomendasi createRekomendasi(Rekomendasi rekomendasi) {
        return rekomendasiRepo.save(rekomendasi);
    }

    @Override
    public Rekomendasi getById(int id_rekomendasi) {
        Optional<Rekomendasi> rekomendasi = rekomendasiRepo.findById(id_rekomendasi);
        if (rekomendasi.isPresent()) return rekomendasi.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Rekomendasi> getAll() {
        return rekomendasiRepo.findAll();
    }

    @Override
    public Rekomendasi updateRekomendasi(int id_rekomendasi, Rekomendasi rekomendasi) {
        Rekomendasi target = getById(id_rekomendasi);
        target.setKeterangan(rekomendasi.getKeterangan());
        target.setTenggat_waktu(rekomendasi.getTenggat_waktu());
        target.setStatus_rekomendasi(rekomendasi.getStatus_rekomendasi());
        target.setPembuat(rekomendasi.getPembuat());
        target.setNama(rekomendasi.getNama());
        target.setKomponen_pemeriksaan(rekomendasi.getKomponen_pemeriksaan());
        return rekomendasiRepo.save(target);
    }

    @Override
    public void deleteRekomendasi(int id_rekomendasi) {
        rekomendasiRepo.deleteById(id_rekomendasi);
    }
}
