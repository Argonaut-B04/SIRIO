package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;

import java.awt.*;
import java.util.List;

public interface BuktiPelaksanaanRestService {
    BuktiPelaksanaan createBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan);

    BuktiPelaksanaan getById(int id_bukti_pelaksanaan);

    List<BuktiPelaksanaan> getAll();

    BuktiPelaksanaan updateBuktiPelaksanaan(int id_bukti_pelaksanaan, BuktiPelaksanaan buktiPelaksanaan);

    void deleteBuktiPelaksanaan(int id_bukti_pelaksanaan);
}
