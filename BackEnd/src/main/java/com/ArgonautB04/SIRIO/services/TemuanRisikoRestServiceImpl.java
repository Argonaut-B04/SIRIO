package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TemuanRisiko;
import com.ArgonautB04.SIRIO.repository.TemuanRisikoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TemuanRisikoRestServiceImpl implements TemuanRisikoRestService {

    @Autowired
    private TemuanRisikoDB temuanRisikoDB;

    @Override
    public TemuanRisiko createTemuanRisiko(TemuanRisiko temuanRisiko) {
        return temuanRisikoDB.save(temuanRisiko);
    }

    @Override
    public TemuanRisiko getById(int idTemuanRisiko) {
        Optional<TemuanRisiko> temuanRisiko = temuanRisikoDB.findById(idTemuanRisiko);
        if (temuanRisiko.isPresent()) return temuanRisiko.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<TemuanRisiko> getAll() {
        return temuanRisikoDB.findAll();
    }

    @Override
    public TemuanRisiko updateTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko) {
        TemuanRisiko target = getById(idTemuanRisiko);
        target.setPembuat(temuanRisiko.getPembuat());
        target.setKomponenPemeriksaan(temuanRisiko.getKomponenPemeriksaan());
        target.setKeterangan(temuanRisiko.getKeterangan());
        return temuanRisikoDB.save(target);
    }

    @Override
    public void deleteTemuanRisiko(int idTemuanRisiko) {
        temuanRisikoDB.deleteById(idTemuanRisiko);
    }
}
