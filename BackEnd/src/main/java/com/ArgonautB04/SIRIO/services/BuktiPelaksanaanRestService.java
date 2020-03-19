package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;

import java.awt.*;
import java.util.List;

public interface BuktiPelaksanaanRestService {
    BuktiPelaksanaan buatBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan);

    BuktiPelaksanaan getById(int idBuktiPelaksanaan);

    List<BuktiPelaksanaan> getAll();

    BuktiPelaksanaan ubahBuktiPelaksanaan(int idBuktiPelaksanaan, BuktiPelaksanaan buktiPelaksanaan);

    List<BuktiPelaksanaan> getByPembuat(Employee pembuat);
}
