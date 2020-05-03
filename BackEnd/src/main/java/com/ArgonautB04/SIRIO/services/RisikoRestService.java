package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;

import java.util.List;
import java.util.Optional;

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

    boolean isExistInDatabase(Risiko risiko);

    RisikoDTO ubahHierarki(Risiko risikoAwal, RisikoDTO risikoBaru);

    List<Risiko> getParentByKategori(Integer kategori);

    Optional<Risiko> getByNama(String nama);
}
