package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import com.argonautb04.sirio.repository.HasilPemeriksaanDB;
import com.argonautb04.sirio.repository.StatusHasilPemeriksaanDB;
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
public class HasilPemeriksaanRestServiceImpl implements HasilPemeriksaanRestService {

    @Autowired
    private HasilPemeriksaanDB hasilPemeriksaanDB;

    @Autowired
    private StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;

    @Override
    public HasilPemeriksaan buatHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan) {
        return hasilPemeriksaanDB.save(hasilPemeriksaan);
    }

    @Override
    public HasilPemeriksaan getById(int idHasilPemeriksaan) {
        Optional<HasilPemeriksaan> hasilPemeriksaan = hasilPemeriksaanDB.findById(idHasilPemeriksaan);
        if (hasilPemeriksaan.isPresent())
            return hasilPemeriksaan.get();
        else
            throw new NoSuchElementException();
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
    public Optional<HasilPemeriksaan> getByTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan) {
        return hasilPemeriksaanDB.findByTugasPemeriksaan(tugasPemeriksaan);
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
    public HasilPemeriksaan ubahStatus(int idHasilPemeriksaan, int idStatus) {
        HasilPemeriksaan target = getById(idHasilPemeriksaan);
        target.setStatusHasilPemeriksaan(statusHasilPemeriksaanDB.findById(idStatus).get());
        return hasilPemeriksaanDB.save(target);
    }

    @Override
    public void hapusHasilPemeriksaan(int idHasilPemeriksaan) {
        hasilPemeriksaanDB.deleteById(idHasilPemeriksaan);
    }

    @Override
    public HasilPemeriksaan validateExistById(int idHasilPemeriksaan) {
        Optional<HasilPemeriksaan> target = hasilPemeriksaanDB.findById(idHasilPemeriksaan);
        if (target.isPresent()) {
            return target.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Hasil pemeriksaan dengan id " + idHasilPemeriksaan + " tidak ditemukan");
        }
    }
}
