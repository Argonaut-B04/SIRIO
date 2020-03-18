package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;

import java.util.List;

public interface RisikoRestService {
    Risiko buatRisiko(Risiko risiko);

    Risiko getById(int idRisiko);

    List<Risiko> getAll();

    Risiko ubahRisiko(int idRisiko, Risiko risiko);

    void hapusRisiko(int idRisiko);

    Risiko nonaktifkanRisiko(int idRisiko);

    Risiko aktifkanRisiko(int idRisiko);
}
