package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.repository.HasilPemeriksaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class HasilPemeriksaanRestServiceImpl implements HasilPemeriksaanRestService {

    @Autowired
    private HasilPemeriksaanRepo hasilPemeriksaanRepo;

    @Override
    public HasilPemeriksaan createHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan) {
        return hasilPemeriksaanRepo.save(hasilPemeriksaan);
    }

    @Override
    public HasilPemeriksaan getById(int id_hasil_pemeriksaan) {
        Optional<HasilPemeriksaan> hasilPemeriksaan = hasilPemeriksaanRepo.findById(id_hasil_pemeriksaan);
        if (hasilPemeriksaan.isPresent()) return hasilPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public HasilPemeriksaan updateHasilPemeriksaan(int id_hasil_pemeriksaan, HasilPemeriksaan hasilPemeriksaan) {
        HasilPemeriksaan target = getById(id_hasil_pemeriksaan);
        target.setFeedback(hasilPemeriksaan.getFeedback());
        target.setTugas_pemeriksaan(hasilPemeriksaan.getTugas_pemeriksaan());
        target.setStatus_hasil_pemeriksaan(hasilPemeriksaan.getStatus_hasil_pemeriksaan());
        target.setPemeriksa(hasilPemeriksaan.getPemeriksa());
        target.setPembuat(hasilPemeriksaan.getPembuat());
        target.setNama_hasil_pemeriksaan(hasilPemeriksaan.getNama_hasil_pemeriksaan());
        return hasilPemeriksaanRepo.save(target);
    }

    @Override
    public void deleteHasilPemeriksaan(int id_hasil_pemeriksaan) {
        hasilPemeriksaanRepo.deleteById(id_hasil_pemeriksaan);
    }
}
