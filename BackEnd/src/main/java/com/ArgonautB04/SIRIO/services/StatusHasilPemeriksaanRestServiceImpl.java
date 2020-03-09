package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusHasilPemeriksaan;
import com.ArgonautB04.SIRIO.repository.StatusHasilPemeriksaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusHasilPemeriksaanRestServiceImpl implements StatusHasilPemeriksaanRestService {

    @Autowired
    private StatusHasilPemeriksaanRepo statusHasilPemeriksaanRepo;

    @Override
    public StatusHasilPemeriksaan createStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan) {
        return statusHasilPemeriksaanRepo.save(statusHasilPemeriksaan);
    }

    @Override
    public StatusHasilPemeriksaan getById(int id_status) {
        Optional<StatusHasilPemeriksaan> statusHasilPemeriksaan = statusHasilPemeriksaanRepo.findById(id_status);
        if (statusHasilPemeriksaan.isPresent()) {
            return statusHasilPemeriksaan.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
