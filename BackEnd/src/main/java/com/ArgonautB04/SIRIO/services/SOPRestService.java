package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.SOP;

import java.util.List;

public interface SOPRestService {
    SOP createSOP(SOP sop);

    SOP getById(int idSop);

    List<SOP> getAll();

    SOP updateSOP(int idSop, SOP sop);

    void deleteSOP(int idSop);
}
