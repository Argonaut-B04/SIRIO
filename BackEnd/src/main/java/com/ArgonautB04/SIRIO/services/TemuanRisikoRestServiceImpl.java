package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.repository.*;
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

    @Autowired
    private KomponenPemeriksaanDB komponenPemeriksaanDB;

    @Autowired
    private HasilPemeriksaanDB hasilPemeriksaanDB;

    @Autowired
    private TugasPemeriksaanDB tugasPemeriksaanDB;

    @Autowired
    private StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;

    @Override
    public TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko) {
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
    public List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return temuanRisikoDB.findAllByKomponenPemeriksaan(komponenPemeriksaan);
    }

    @Override
    public List<TemuanRisiko> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList) {
        return temuanRisikoDB.findAllByKomponenPemeriksaanIn(komponenPemeriksaanList);
    }

    @Override
    public List<TemuanRisiko> getHistoriTemuanRisikoKantorCabang(TugasPemeriksaan tugasPemeriksaan, Risiko risiko) {
        StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanDB.findById(5).get();
        List<TugasPemeriksaan> tugasPemeriksaans = tugasPemeriksaanDB.findAllByKantorCabang(
                tugasPemeriksaan.getKantorCabang());
        List<HasilPemeriksaan> hasilPemeriksaans = hasilPemeriksaanDB.findAllByTugasPemeriksaanInAndStatusHasilPemeriksaan(
                tugasPemeriksaans, statusHasilPemeriksaan);
        List<KomponenPemeriksaan> komponenPemeriksaans = komponenPemeriksaanDB.findAllByHasilPemeriksaanInAndRisiko(hasilPemeriksaans, risiko);

        return temuanRisikoDB.findAllByKomponenPemeriksaanIn(komponenPemeriksaans);
    }

    @Override
    public TemuanRisiko ubahTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko) {
        TemuanRisiko target = getById(idTemuanRisiko);
        target.setPembuat(temuanRisiko.getPembuat());
        target.setKomponenPemeriksaan(temuanRisiko.getKomponenPemeriksaan());
        target.setKeterangan(temuanRisiko.getKeterangan());
        return temuanRisikoDB.save(target);
    }

    @Override
    public void hapusTemuanRisiko(int idTemuanRisiko) {
        temuanRisikoDB.deleteById(idTemuanRisiko);
    }
}
