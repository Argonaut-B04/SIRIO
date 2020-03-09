package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.repository.KomponenPemeriksaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class KomponenPemeriksaanRestServiceImpl implements KomponenPemeriksaanRestService {

    @Autowired
    private KomponenPemeriksaanRepo komponenPemeriksaanRepo;

    @Override
    public KomponenPemeriksaan createKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return komponenPemeriksaanRepo.save(komponenPemeriksaan);
    }

    @Override
    public KomponenPemeriksaan getById(int id_komponen_pemeriksaan) {
        Optional<KomponenPemeriksaan> komponenPemeriksaan = komponenPemeriksaanRepo.findById(id_komponen_pemeriksaan);
        if (komponenPemeriksaan.isPresent()) {
            return komponenPemeriksaan.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public KomponenPemeriksaan updateKomponenPemeriksaan(int id_komponen_pemeriksaan, KomponenPemeriksaan komponenPemeriksaan) {
        KomponenPemeriksaan target = getById(id_komponen_pemeriksaan);
        target.setHasil_pemeriksaan(komponenPemeriksaan.getHasil_pemeriksaan());
        target.setRisk_level(komponenPemeriksaan.getRisk_level());
        target.setRisiko(komponenPemeriksaan.getRisiko());
        target.setKeterangan_sampel(komponenPemeriksaan.getKeterangan_sampel());
        target.setJumlah_sampel(komponenPemeriksaan.getJumlah_sampel());
        return komponenPemeriksaanRepo.save(target);
    }

    @Override
    public void deleteKomponenPemeriksaan(int id_komponen_pemeriksaan) {
        komponenPemeriksaanRepo.deleteById(id_komponen_pemeriksaan);
    }
}
