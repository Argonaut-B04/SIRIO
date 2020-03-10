package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.SOP;

import java.util.List;

public interface SOPRestService {
    SOP buatSOP(SOP sop);

    SOP getById(int idSop);

    List<SOP> getAll();

    SOP ubahSOP(int idSop, SOP sop);

    void hapusSOP(int idSop);
}
