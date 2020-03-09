package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;

import java.util.List;

public interface RekomendasiRestService {
    Rekomendasi createRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int id_rekomendasi);

    List<Rekomendasi> getAll();

    Rekomendasi updateRekomendasi(int id_rekomendasi, Rekomendasi rekomendasi);

    void deleteRekomendasi(int id_rekomendasi);
}
