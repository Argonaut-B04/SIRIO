package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.repository.KomponenPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class KomponenPemeriksaanRestServiceImpl implements KomponenPemeriksaanRestService {

    @Autowired
    private KomponenPemeriksaanDB komponenPemeriksaanDB;

    @Override
    public KomponenPemeriksaan buatKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return komponenPemeriksaanDB.save(komponenPemeriksaan);
    }

    @Override
    public KomponenPemeriksaan getById(int idKomponenPemeriksaan) {
        Optional<KomponenPemeriksaan> komponenPemeriksaan = komponenPemeriksaanDB.findById(idKomponenPemeriksaan);
        if (komponenPemeriksaan.isPresent()) return komponenPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<KomponenPemeriksaan> getAll() {
        return komponenPemeriksaanDB.findAll();
    }

    @Override
    public List<KomponenPemeriksaan> getByHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan) {
        return komponenPemeriksaanDB.findAllByHasilPemeriksaan(hasilPemeriksaan);
    }

    @Override
    public List<KomponenPemeriksaan> getByDaftarHasilPemeriksaan(List<HasilPemeriksaan> hasilPemeriksaanList) {
        return komponenPemeriksaanDB.findAllByHasilPemeriksaanIn(hasilPemeriksaanList);
    }

    @Override
    public KomponenPemeriksaan ubahKomponenPemeriksaan(int idKomponenPemeriksaan, KomponenPemeriksaan komponenPemeriksaan) {
        KomponenPemeriksaan target = getById(idKomponenPemeriksaan);
        target.setHasilPemeriksaan(komponenPemeriksaan.getHasilPemeriksaan());
        target.setRiskLevel(komponenPemeriksaan.getRiskLevel());
        target.setRisiko(komponenPemeriksaan.getRisiko());
        target.setKeteranganSampel(komponenPemeriksaan.getKeteranganSampel());
        target.setJumlahSampel(komponenPemeriksaan.getJumlahSampel());
        return komponenPemeriksaanDB.save(target);
    }

    @Override
    public void hapusKomponenPemeriksaan(int idKomponenPemeriksaan) {
        komponenPemeriksaanDB.deleteById(idKomponenPemeriksaan);
    }

    @Override
    public KomponenPemeriksaan getByRisiko(Risiko risiko) {
        return komponenPemeriksaanDB.findAllByRisiko(risiko);
    }
}
