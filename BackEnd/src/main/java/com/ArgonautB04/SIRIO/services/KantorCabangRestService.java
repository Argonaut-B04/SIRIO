package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KantorCabang;

import java.util.List;

public interface KantorCabangRestService {
    KantorCabang buatKantorCabang(KantorCabang kantorCabang);

    KantorCabang getById(int idKantorCabang);

    List<KantorCabang> getAll();

    KantorCabang ubahKantorCabang(int idKantorCabang, KantorCabang kantorCabang);

    void hapusKantorCabang(int idKantorCabang);
}
