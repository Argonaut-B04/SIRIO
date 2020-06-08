package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.BuktiPelaksanaan;
import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.Rekomendasi;

import java.util.List;

public interface BuktiPelaksanaanRestService {
    BuktiPelaksanaan buatBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan);

    BuktiPelaksanaan getById(int idBuktiPelaksanaan);

    BuktiPelaksanaan getByRekomendasi(Rekomendasi rekomendasi);

    List<BuktiPelaksanaan> getAll();

    BuktiPelaksanaan ubahBuktiPelaksanaan(int idBuktiPelaksanaan, BuktiPelaksanaan buktiPelaksanaan);

    List<BuktiPelaksanaan> getByPembuat(Employee pembuat);

    List<BuktiPelaksanaan> getByDaftarRekomendasi(List<Rekomendasi> rekomendasiList);

    BuktiPelaksanaan validateExistById(int idBuktiPelaksanaan);
}
