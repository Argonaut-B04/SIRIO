package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.repository.TugasPemeriksaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TugasPemeriksaanRestServiceImpl implements TugasPemeriksaanRestService {

    @Autowired
    private TugasPemeriksaanRepo tugasPemeriksaanRepo;

    @Override
    public TugasPemeriksaan createTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan) {
        return tugasPemeriksaanRepo.save(tugasPemeriksaan);
    }

    @Override
    public TugasPemeriksaan getById(int id_tugas_pemeriksaan) {
        Optional<TugasPemeriksaan> tugasPemeriksaan = tugasPemeriksaanRepo.findById(id_tugas_pemeriksaan);
        if (tugasPemeriksaan.isPresent()) return tugasPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public TugasPemeriksaan updateTugasPemeriksaan(int id_tugas_pemeriksaan, TugasPemeriksaan tugasPemeriksaan) {
        TugasPemeriksaan target = getById(id_tugas_pemeriksaan);
        target.setTanggal_selesai(tugasPemeriksaan.getTanggal_selesai());
        target.setTanggal_mulai(tugasPemeriksaan.getTanggal_mulai());
        target.setRencanaPemeriksaan(tugasPemeriksaan.getRencanaPemeriksaan());
        target.setPelaksana(tugasPemeriksaan.getPelaksana());
        target.setKantor_cabang(tugasPemeriksaan.getKantor_cabang());
        return tugasPemeriksaanRepo.save(target);
    }

    @Override
    public void deleteTugasPemeriksaan(int id_tugas_pemeriksaan) {
        tugasPemeriksaanRepo.deleteById(id_tugas_pemeriksaan);
    }
}
