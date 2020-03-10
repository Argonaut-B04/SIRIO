package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRencanaPemeriksaan;
import com.ArgonautB04.SIRIO.repository.StatusRencanaPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusRencanaPemeriksaanRestServiceImpl implements StatusRencanaPemeriksaanRestService {

    @Autowired
    private StatusRencanaPemeriksaanDB statusRencanaPemeriksaanDB;

    @Override
    public StatusRencanaPemeriksaan createStatusRencanaPemeriksaan(StatusRencanaPemeriksaan statusRencanaPemeriksaan) {
        return statusRencanaPemeriksaanDB.save(statusRencanaPemeriksaan);
    }

    @Override
    public StatusRencanaPemeriksaan getById(int idStatus) {
        Optional<StatusRencanaPemeriksaan> statusRencanaPemeriksaan = statusRencanaPemeriksaanDB.findById(idStatus);
        if (statusRencanaPemeriksaan.isPresent()) return statusRencanaPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<StatusRencanaPemeriksaan> getAll() {
        return statusRencanaPemeriksaanDB.findAll();
    }
}
