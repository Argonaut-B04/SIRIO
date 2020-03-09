package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRisiko;

public interface StatusRisikoRestService {
    StatusRisiko createStatusRisiko(StatusRisiko statusRisiko);

    StatusRisiko getById(int id_status);
}
