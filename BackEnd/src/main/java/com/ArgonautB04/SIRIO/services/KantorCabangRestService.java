package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KantorCabang;

import java.util.List;
import java.util.Optional;

public interface KantorCabangRestService {
    KantorCabang buatKantorCabang(KantorCabang kantorCabang);

    KantorCabang getById(int idKantorCabang);

    Optional<KantorCabang> getByNama(String nama);

    KantorCabang getByPemilik(Employee pemilik);

    List<KantorCabang> getByArea(String area);

    List<KantorCabang> getByRegional(String regional);

    List<KantorCabang> getByAreaAndRegional(String area, String regional);

    KantorCabang nonaktifkanKantor(int idKantor);

    KantorCabang aktifkanKantor(int idKantor);

    List<KantorCabang> getAll();

    KantorCabang ubahKantorCabang(int idKantorCabang, KantorCabang kantorCabang);

    void hapusKantorCabang(int idKantorCabang);

    // List<KantorCabang> getByPembuat(Employee pembuat);

    // KantorCabang validateExistInDatabase(int idKantorCabang);

    KantorCabang validateExistById(int idKantorCabang);

    void nullifiedRiskRating();

    void recalculateRiskRating();

}
