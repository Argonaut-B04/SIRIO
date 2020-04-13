package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRekomendasi;
import com.ArgonautB04.SIRIO.repository.StatusRekomendasiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusRekomendasiRestServiceImpl implements StatusRekomendasiRestService {

    @Autowired
    private StatusRekomendasiDB statusRekomendasiDB;

    @Override
    public StatusRekomendasi buatStatusRekomendasi(StatusRekomendasi statusRekomendasi) {
        return statusRekomendasiDB.save(statusRekomendasi);
    }

    @Override
    public StatusRekomendasi getById(int idStatus) {
        Optional<StatusRekomendasi> statusRekomendasi = statusRekomendasiDB.findById(idStatus);
        if (statusRekomendasi.isPresent()) return statusRekomendasi.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<StatusRekomendasi> getAll() {
        return statusRekomendasiDB.findAll();
    }

    @Override
    public StatusRekomendasi getByNamaStatus(String namaStatus) {
        Optional<StatusRekomendasi> target = statusRekomendasiDB.findByNamaStatus(namaStatus);
        if (target.isPresent()) return target.get();
        else throw new NoSuchElementException();
    }
}
