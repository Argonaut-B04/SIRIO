package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.repository.RekomendasiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RekomendasiRestServiceImpl implements RekomendasiRestService {

    @Autowired
    private RekomendasiDB rekomendasiDB;

    @Autowired
    private StatusRekomendasiRestService statusRekomendasiRestService;

    @Override
    public Rekomendasi buatRekomendasi(Rekomendasi rekomendasi) {
        return rekomendasiDB.save(rekomendasi);
    }

    @Override
    public Rekomendasi getById(int idRekomendasi) {
        Optional<Rekomendasi> rekomendasi = getOptionalById(idRekomendasi);
        if (rekomendasi.isPresent()) return rekomendasi.get();
        else throw new NoSuchElementException();
    }

    @Override
    public Optional<Rekomendasi> getOptionalById(int idRekomendasi) {
        return rekomendasiDB.findById(idRekomendasi);
    }

    @Override
    public List<Rekomendasi> getAll() {
        return rekomendasiDB.findAll();
    }

    @Override
    public void ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi) {
        Rekomendasi target = getById(idRekomendasi);
        target.setKeterangan(rekomendasi.getKeterangan());
        target.setTenggatWaktu(rekomendasi.getTenggatWaktu());
        target.setStatusRekomendasi(rekomendasi.getStatusRekomendasi());
        target.setPembuat(rekomendasi.getPembuat());
        target.setKomponenPemeriksaan(rekomendasi.getKomponenPemeriksaan());
        target.setBuktiPelaksanaan(rekomendasi.getBuktiPelaksanaan());
        rekomendasiDB.save(target);
    }

    @Override
    public void hapusRekomendasi(int idRekomendasi) {
        rekomendasiDB.deleteById(idRekomendasi);
    }

    @Override
    public Rekomendasi ubahTenggatWaktu(Rekomendasi rekomendasi, Date tenggatWaktuDate) {
        rekomendasi.setTenggatWaktu(tenggatWaktuDate);
        rekomendasi.setStatusRekomendasi(statusRekomendasiRestService.getByNamaStatus("Menunggu Pelaksanaan"));
        return rekomendasiDB.save(rekomendasi);
    }

    @Override
    public List<Rekomendasi> getByPembuat(Employee pembuat) {
        return rekomendasiDB.findAllByPembuat(pembuat);
    }

    @Override
    public List<Rekomendasi> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return rekomendasiDB.findAllByKomponenPemeriksaan(komponenPemeriksaan);
    }

    @Override
    public List<Rekomendasi> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList) {
        return rekomendasiDB.findAllByKomponenPemeriksaanIn(komponenPemeriksaanList);
    }
}
