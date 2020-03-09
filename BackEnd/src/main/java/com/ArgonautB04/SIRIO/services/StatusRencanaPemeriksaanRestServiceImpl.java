package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRencanaPemeriksaan;
import com.ArgonautB04.SIRIO.repository.StatusRencanaPemeriksaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusRencanaPemeriksaanRestServiceImpl implements StatusRencanaPemeriksaanRestService {

    @Autowired
    private StatusRencanaPemeriksaanRepo statusRencanaPemeriksaanRepo;

    @Override
    public StatusRencanaPemeriksaan createStatusRencanaPemeriksaan(StatusRencanaPemeriksaan statusRencanaPemeriksaan) {
        return statusRencanaPemeriksaanRepo.save(statusRencanaPemeriksaan);
    }

    @Override
    public StatusRencanaPemeriksaan getById(int id_status) {
        Optional<StatusRencanaPemeriksaan> statusRencanaPemeriksaan = statusRencanaPemeriksaanRepo.findById(id_status);
        if (statusRencanaPemeriksaan.isPresent()) {
            return statusRencanaPemeriksaan.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
