package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.repository.TugasPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TugasPemeriksaanRestServiceImpl implements TugasPemeriksaanRestService {

    @Autowired
    private TugasPemeriksaanDB tugasPemeriksaanDB;

    @Override
    public TugasPemeriksaan buatTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan) {
        return tugasPemeriksaanDB.save(tugasPemeriksaan);
    }

    @Override
    public TugasPemeriksaan getById(int idTugasPemeriksaan) {
        Optional<TugasPemeriksaan> tugasPemeriksaan = tugasPemeriksaanDB.findById(idTugasPemeriksaan);
        if (tugasPemeriksaan.isPresent()) return tugasPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<TugasPemeriksaan> getAll() {
        return tugasPemeriksaanDB.findAll();
    }

    @Override
    public TugasPemeriksaan ubahTugasPemeriksaan(int idTugasPemeriksaan, TugasPemeriksaan tugasPemeriksaan) {
        TugasPemeriksaan target = getById(idTugasPemeriksaan);
        target.setTanggalSelesai(tugasPemeriksaan.getTanggalSelesai());
        target.setTanggalMulai(tugasPemeriksaan.getTanggalMulai());
        target.setRencanaPemeriksaan(tugasPemeriksaan.getRencanaPemeriksaan());
        target.setPelaksana(tugasPemeriksaan.getPelaksana());
        target.setKantorCabang(tugasPemeriksaan.getKantorCabang());
        return tugasPemeriksaanDB.save(target);
    }

    @Override
    public void hapusTugasPemeriksaan(int idTugasPemeriksaan) {
        tugasPemeriksaanDB.deleteById(idTugasPemeriksaan);
    }
}
