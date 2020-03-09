package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRekomendasi;

public interface StatusRekomendasiRestService {
    StatusRekomendasi createStatusRekomendasi(StatusRekomendasi statusRekomendasi);

    StatusRekomendasi getById(int id_status);
}
