package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.StatusHasilPemeriksaan;
import com.argonautb04.sirio.repository.StatusHasilPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusHasilPemeriksaanRestServiceImpl implements StatusHasilPemeriksaanRestService {

    @Autowired
    private StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;

    @Override
    public StatusHasilPemeriksaan buatStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan) {
        return statusHasilPemeriksaanDB.save(statusHasilPemeriksaan);
    }

    @Override
    public StatusHasilPemeriksaan getById(int idStatus) {
        Optional<StatusHasilPemeriksaan> statusHasilPemeriksaan = statusHasilPemeriksaanDB.findById(idStatus);
        if (statusHasilPemeriksaan.isPresent())
            return statusHasilPemeriksaan.get();
        else
            throw new NoSuchElementException();
    }

    @Override
    public List<StatusHasilPemeriksaan> getAll() {
        return statusHasilPemeriksaanDB.findAll();
    }
}
