package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;

import java.awt.*;
import java.util.List;

public interface BuktiPelaksanaanRestService {
    BuktiPelaksanaan createBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan);

    BuktiPelaksanaan getById(int idBuktiPelaksanaan);

    List<BuktiPelaksanaan> getAll();

    BuktiPelaksanaan updateBuktiPelaksanaan(int idBuktiPelaksanaan, BuktiPelaksanaan buktiPelaksanaan);

    void deleteBuktiPelaksanaan(int idBuktiPelaksanaan);
}
