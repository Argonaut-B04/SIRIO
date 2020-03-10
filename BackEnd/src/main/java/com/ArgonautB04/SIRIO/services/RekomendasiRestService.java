package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;

import java.util.List;

public interface RekomendasiRestService {
    Rekomendasi createRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int idRekomendasi);

    List<Rekomendasi> getAll();

    Rekomendasi updateRekomendasi(int idRekomendasi, Rekomendasi rekomendasi);

    void deleteRekomendasi(int idRekomendasi);
}
