package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.KantorCabang;

public interface KantorCabangRestService {
    KantorCabang createKantorCabang(KantorCabang kantorCabang);

    KantorCabang getById(int id_kantor_cabang);

    KantorCabang updateKantorCabang(int id_kantor_cabang, KantorCabang kantorCabang);

    void deleteKantorCabang(int id_kantor_cabang);
}
