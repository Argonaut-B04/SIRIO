package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRisiko;

import java.util.List;

public interface StatusRisikoRestService {
    StatusRisiko createStatusRisiko(StatusRisiko statusRisiko);

    StatusRisiko getById(int idStatus);

    List<StatusRisiko> getAll();
}
