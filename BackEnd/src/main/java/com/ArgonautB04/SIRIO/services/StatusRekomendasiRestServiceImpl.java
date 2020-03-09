package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRekomendasi;
import com.ArgonautB04.SIRIO.repository.StatusRekomendasiRepo;
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
    private StatusRekomendasiRepo statusRekomendasiRepo;

    @Override
    public StatusRekomendasi createStatusRekomendasi(StatusRekomendasi statusRekomendasi) {
        return statusRekomendasiRepo.save(statusRekomendasi);
    }

    @Override
    public StatusRekomendasi getById(int id_status) {
        Optional<StatusRekomendasi> statusRekomendasi = statusRekomendasiRepo.findById(id_status);
        if (statusRekomendasi.isPresent()) return statusRekomendasi.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<StatusRekomendasi> getAll() {
        return statusRekomendasiRepo.findAll();
    }
}
