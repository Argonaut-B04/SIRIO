package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.SOP;

import java.util.List;
import java.util.Optional;

public interface SOPRestService {
    SOP buatSOP(SOP sop);

    SOP getById(int idSop);

    List<SOP> getAll();

    SOP ubahSOP(int idSop, SOP sop);

    Optional<SOP> getByJudul(String judul);

    void hapusSOP(int idSop);

    SOP validateExistById(int idSOP);

    SOP nonaktifkanSOP(int idSOP);
}
