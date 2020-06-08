package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.StatusBuktiPelaksanaan;

import java.util.List;

public interface StatusBuktiPelaksanaanRestService {
    StatusBuktiPelaksanaan buatStatusBuktiPelaksanaan(StatusBuktiPelaksanaan statusBuktiPelaksanaan);

    StatusBuktiPelaksanaan getById(int idStatus);

    List<StatusBuktiPelaksanaan> getAll();
}
