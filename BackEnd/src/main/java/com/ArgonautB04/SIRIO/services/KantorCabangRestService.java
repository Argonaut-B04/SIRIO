package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;

import java.util.List;
import java.util.Optional;

public interface KantorCabangRestService {
    KantorCabang buatKantorCabang(KantorCabang kantorCabang);

    KantorCabang getById(int idKantorCabang);

    Optional<KantorCabang> getByNama (String nama);

    KantorCabang getByPemilik(Employee pemilik);

    KantorCabang nonaktifkanKantor(int idKantor);

    KantorCabang aktifkanKantor(int idKantor);

    List<KantorCabang> getAll();

   // boolean isExistInDatabase(KantorCabang kantorCabang);

    //KantorCabang isExistInDatabase(String namaRiskLevel);

    KantorCabang ubahKantorCabang(int idKantorCabang, KantorCabang kantorCabang);

    void hapusKantorCabang(int idKantorCabang);

<<<<<<< HEAD
    //List<KantorCabang> getByPembuat(Employee pembuat);
=======
    KantorCabang validateExistInDatabase(int idKantorCabang);
>>>>>>> 3d62f554b8f925e99d1cc0922aa6f9b35cccd47d
}
