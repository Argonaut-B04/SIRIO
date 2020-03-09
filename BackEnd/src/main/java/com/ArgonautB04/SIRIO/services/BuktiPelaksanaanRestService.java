package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;

public interface BuktiPelaksanaanRestService {
    BuktiPelaksanaan createBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan);

    BuktiPelaksanaan getById(int id_bukti_pelaksanaan);

    BuktiPelaksanaan updateBuktiPelaksanaan(int id_bukti_pelaksanaan, BuktiPelaksanaan buktiPelaksanaan);

    void deleteBuktiPelaksanaan(int id_bukti_pelaksanaan);
}
