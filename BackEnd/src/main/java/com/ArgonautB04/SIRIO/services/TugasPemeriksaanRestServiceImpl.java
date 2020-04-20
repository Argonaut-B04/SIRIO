package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.repository.TugasPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<TugasPemeriksaan> getByPelaksana(Employee pelaksana) {
        return tugasPemeriksaanDB.findAllByPelaksana(pelaksana);
    }

    @Override
    public List<TugasPemeriksaan> getByKantorCabang(KantorCabang kantorCabang) {
        return tugasPemeriksaanDB.findAllByKantorCabang(kantorCabang);
    }

    @Override
    public List<TugasPemeriksaan> getByRencana(RencanaPemeriksaan rencanaPemeriksaan) {
        return tugasPemeriksaanDB.findAllByRencanaPemeriksaan(rencanaPemeriksaan);
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
    public boolean isExistInDatabase(TugasPemeriksaan tugasPemeriksaan) {
        return tugasPemeriksaanDB.findById(tugasPemeriksaan.getIdTugas()).isPresent();
    }

    @Override
    public void hapusTugasPemeriksaan(int idTugasPemeriksaan) {
        tugasPemeriksaanDB.deleteById(idTugasPemeriksaan);
    }

    @Override
    public TugasPemeriksaan validateExistById(int idTugasPemeriksaan) {
        Optional<TugasPemeriksaan> target = tugasPemeriksaanDB.findById(idTugasPemeriksaan);
        if (target.isPresent()) {
            return target.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Tugas pemeriksaan dengan id " + idTugasPemeriksaan + "tidak dapat ditemukan"
            );
        }
    }
}
