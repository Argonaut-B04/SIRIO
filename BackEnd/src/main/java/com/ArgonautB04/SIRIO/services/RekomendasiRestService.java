package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;

public interface RekomendasiRestService {
    Rekomendasi createRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int id_rekomendasi);

    Rekomendasi updateRekomendasi(int id_rekomendasi, Rekomendasi rekomendasi);

    void deleteRekomendasi(int id_rekomendasi);
}
