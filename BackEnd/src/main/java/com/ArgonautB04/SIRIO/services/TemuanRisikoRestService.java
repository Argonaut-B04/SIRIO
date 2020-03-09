package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TemuanRisiko;

public interface TemuanRisikoRestService {
    TemuanRisiko createTemuanRisiko(TemuanRisiko temuanRisiko);

    TemuanRisiko getById(int id_temuan_risiko);

    TemuanRisiko updateTemuanRisiko(int id_temuan_risiko, TemuanRisiko temuanRisiko);

    void deleteTemuanRisiko(int id_temuan_risiko);
}
