package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.repository.RekomendasiDB;
import com.ArgonautB04.SIRIO.repository.StatusRekomendasiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Rekomendasi> getRekomendasiBelumDiimplementasi() {
        List<Rekomendasi> belumImpl = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getBuktiPelaksanaan() != null && r.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().equals(1)) {
                belumImpl.add(r);
            }
        }
        return belumImpl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiOverdue() {
        List<Rekomendasi> overdue = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getTenggatWaktu() != null && LocalDate.now().isAfter(r.getTenggatWaktu()) && r.getStatusRekomendasi() != null
                    && !r.getStatusRekomendasi().equals(7)) {
                overdue.add(r);
            }
        }
        return overdue;
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
    public List<Rekomendasi> getByPembuat(Employee pembuat) {
        return rekomendasiDB.findAllByPembuat(pembuat);
    }

    @Override
    public Rekomendasi validateExistInById(int idRekomendasi) {
        Optional<Rekomendasi> rekomendasiOptional = getOptionalById(idRekomendasi);
        if (rekomendasiOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        } else {
            return rekomendasiOptional.get();
        }
    }

    @Override
    public void validateDateInputMoreThanToday(LocalDate localDate) {
        if (localDate.compareTo(LocalDate.now()) < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pastikan tenggat waktu yang anda masukan belum terlewat"
            );
        }
    }

    @Override
    public void validateDeadlineCanBeSet(Rekomendasi rekomendasi) {
        if (!rekomendasi.getStatusRekomendasi().isDapatSetTenggatWaktu()) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED,
                    "Tenggat waktu rekomendasi belum dapat diatur!"
            );
        }
    }

    @Override
    public Rekomendasi buatAtauSimpanPerubahanRekomendasi(Rekomendasi rekomendasi, boolean tenggatWaktuTerubah) {
        if (tenggatWaktuTerubah) {
            rekomendasi.setStatusRekomendasi(
                    statusRekomendasiRestService.getByNamaStatus("Menunggu Pelaksanaan")
            );
        }
        return rekomendasiDB.save(rekomendasi);
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
