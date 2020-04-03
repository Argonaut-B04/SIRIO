package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;

import java.util.List;

public interface RisikoRestService {
    Risiko buatRisiko(Risiko risiko);

    Risiko getById(int idRisiko);

    List<Risiko> getAll();

    List<Risiko> getByKategori(Integer kategori);

    Risiko ubahRisiko(int idRisiko, Risiko risiko);

    void hapusRisiko(int idRisiko);

    Risiko nonaktifkanRisiko(int idRisiko);

    Risiko aktifkanRisiko(int idRisiko);

    Risiko transformasidto(Risiko risiko, RisikoDTO risikoDTO);
}
