package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.StatusBuktiPelaksanaan;
import com.argonautb04.sirio.repository.StatusBuktiPelaksanaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusBuktiPelaksanaanRestServiceImpl implements StatusBuktiPelaksanaanRestService {

    @Autowired
    private StatusBuktiPelaksanaanDB statusBuktiPelaksanaanDB;

    @Override
    public StatusBuktiPelaksanaan buatStatusBuktiPelaksanaan(StatusBuktiPelaksanaan statusBuktiPelaksanaan) {
        return statusBuktiPelaksanaanDB.save(statusBuktiPelaksanaan);
    }

    @Override
    public StatusBuktiPelaksanaan getById(int idStatus) {
        Optional<StatusBuktiPelaksanaan> statusBuktiPelaksanaan = statusBuktiPelaksanaanDB.findById(idStatus);
        if (statusBuktiPelaksanaan.isPresent())
            return statusBuktiPelaksanaan.get();
        else
            throw new NoSuchElementException();
    }

    @Override
    public List<StatusBuktiPelaksanaan> getAll() {
        return statusBuktiPelaksanaanDB.findAll();
    }
}
