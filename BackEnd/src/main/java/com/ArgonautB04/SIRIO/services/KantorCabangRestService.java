package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KantorCabang;

import java.util.List;

public interface KantorCabangRestService {
    KantorCabang createKantorCabang(KantorCabang kantorCabang);

    KantorCabang getById(int id_kantor_cabang);

    List<KantorCabang> getAll();

    KantorCabang updateKantorCabang(int id_kantor_cabang, KantorCabang kantorCabang);

    void deleteKantorCabang(int id_kantor_cabang);
}
