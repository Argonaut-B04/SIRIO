package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusBuktiPelaksanaan;

import java.util.List;

public interface StatusBuktiPelaksanaanRestService {
    StatusBuktiPelaksanaan createStatusBuktiPelaksanaan(StatusBuktiPelaksanaan statusBuktiPelaksanaan);

    StatusBuktiPelaksanaan getById(int idStatus);

    List<StatusBuktiPelaksanaan> getAll();
}
