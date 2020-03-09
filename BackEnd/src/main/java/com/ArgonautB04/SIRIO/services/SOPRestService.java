package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.SOP;

import java.util.List;

public interface SOPRestService {
    SOP createSOP(SOP sop);

    SOP getById(int id_sop);

    List<SOP> getAll();

    SOP updateSOP(int id_sop, SOP sop);

    void deleteSOP(int id_sop);
}
