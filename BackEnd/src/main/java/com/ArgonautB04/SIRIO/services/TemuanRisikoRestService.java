package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.TemuanRisiko;

import java.util.List;

public interface TemuanRisikoRestService {
    TemuanRisiko createTemuanRisiko(TemuanRisiko temuanRisiko);

    TemuanRisiko getById(int idTemuanRisiko);

    List<TemuanRisiko> getAll();

    TemuanRisiko updateTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko);

    void deleteTemuanRisiko(int idTemuanRisiko);
}
