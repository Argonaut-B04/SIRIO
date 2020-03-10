package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;

import java.util.List;

public interface RisikoRestService {
    Risiko createRisiko(Risiko risiko);

    Risiko getById(int idRisiko);

    List<Risiko> getAll();

    Risiko updateRisiko(int idRisiko, Risiko risiko);

    void deleteRisiko(int idRisiko);
}
