package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;

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

    KantorCabang validateExistById(int idKantorCabang);

    void nullifiedRiskRating();

    void recalculateRiskRating();
}
