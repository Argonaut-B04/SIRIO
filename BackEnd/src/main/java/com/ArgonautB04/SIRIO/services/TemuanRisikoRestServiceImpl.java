package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TemuanRisiko;
import com.ArgonautB04.SIRIO.repository.TemuanRisikoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TemuanRisikoRestServiceImpl implements TemuanRisikoRestService {

    @Autowired
    private TemuanRisikoRepo temuanRisikoRepo;

    @Override
    public TemuanRisiko createTemuanRisiko(TemuanRisiko temuanRisiko) {
        return temuanRisikoRepo.save(temuanRisiko);
    }

    @Override
    public TemuanRisiko getById(int id_temuan_risiko) {
        Optional<TemuanRisiko> temuanRisiko = temuanRisikoRepo.findById(id_temuan_risiko);
        if (temuanRisiko.isPresent()) {
            return temuanRisiko.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public TemuanRisiko updateTemuanRisiko(int id_temuan_risiko, TemuanRisiko temuanRisiko) {
        TemuanRisiko target = getById(id_temuan_risiko);
        target.setPembuat(temuanRisiko.getPembuat());
        target.setKomponen_pemeriksaan(temuanRisiko.getKomponen_pemeriksaan());
        target.setKeterangan(temuanRisiko.getKeterangan());
        return temuanRisikoRepo.save(target);
    }

    @Override
    public void deleteTemuanRisiko(int id_temuan_risiko) {
        temuanRisikoRepo.deleteById(id_temuan_risiko);
    }
}
