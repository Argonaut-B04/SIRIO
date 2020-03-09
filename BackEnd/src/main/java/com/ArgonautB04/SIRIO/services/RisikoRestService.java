package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;

public interface RisikoRestService {
    Risiko createRisiko(Risiko risiko);

    Risiko getById(int id_risiko);

    Risiko updateRisiko(int id_risiko, Risiko risiko);

    void deleteRisiko(int id_risiko);
}
