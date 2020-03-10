package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;

import java.util.List;

public interface RekomendasiRestService {
    Rekomendasi buatRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int idRekomendasi);

    List<Rekomendasi> getAll();

    Rekomendasi ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi);

    void hapusRekomendasi(int idRekomendasi);
}
