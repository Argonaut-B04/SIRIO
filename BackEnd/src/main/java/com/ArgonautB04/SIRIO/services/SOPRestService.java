package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.SOP;

public interface SOPRestService {
    SOP createSOP(SOP sop);

    SOP getById(int id_sop);

    SOP updateSOP(int id_sop, SOP sop);

    void deleteSOP(int id_sop);
}
