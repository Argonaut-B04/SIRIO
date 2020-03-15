package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;
import com.ArgonautB04.SIRIO.repository.HasilPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class HasilPemeriksaanRestServiceImpl implements HasilPemeriksaanRestService {

    @Autowired
    private HasilPemeriksaanDB hasilPemeriksaanDB;

    @Override
    public HasilPemeriksaan buatHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan) {
        return hasilPemeriksaanDB.save(hasilPemeriksaan);
    }

    @Override
    public HasilPemeriksaan getById(int idHasilPemeriksaan) {
        Optional<HasilPemeriksaan> hasilPemeriksaan = hasilPemeriksaanDB.findById(idHasilPemeriksaan);
        if (hasilPemeriksaan.isPresent()) return hasilPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<HasilPemeriksaan> getAll() {
        return hasilPemeriksaanDB.findAll();
    }

    @Override
    public List<HasilPemeriksaan> getByPembuat(Employee pembuat) {
        return hasilPemeriksaanDB.findAllByPembuat(pembuat);
    }

    @Override
    public List<HasilPemeriksaan> getByDaftarTugasPemeriksaan(List<TugasPemeriksaan> tugasPemeriksaanList) {
        return hasilPemeriksaanDB.findAllByTugasPemeriksaanIn(tugasPemeriksaanList);
    }

    @Override
    public HasilPemeriksaan buatHasilPemeriksaan(int idHasilPemeriksaan, HasilPemeriksaan hasilPemeriksaan) {
        HasilPemeriksaan target = getById(idHasilPemeriksaan);
        target.setFeedback(hasilPemeriksaan.getFeedback());
        target.setTugasPemeriksaan(hasilPemeriksaan.getTugasPemeriksaan());
        target.setStatusHasilPemeriksaan(hasilPemeriksaan.getStatusHasilPemeriksaan());
        target.setPemeriksa(hasilPemeriksaan.getPemeriksa());
        target.setPembuat(hasilPemeriksaan.getPembuat());
        return hasilPemeriksaanDB.save(target);
    }

    @Override
    public void hapusHasilPemeriksaan(int idHasilPemeriksaan) {
        hasilPemeriksaanDB.deleteById(idHasilPemeriksaan);
    }
}
