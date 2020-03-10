package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KantorCabang;

import java.util.List;

public interface KantorCabangRestService {
    KantorCabang createKantorCabang(KantorCabang kantorCabang);

    KantorCabang getById(int idKantorCabang);

    List<KantorCabang> getAll();

    KantorCabang updateKantorCabang(int idKantorCabang, KantorCabang kantorCabang);

    void deleteKantorCabang(int idKantorCabang);
}
