package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRekomendasi;

import java.util.List;

public interface StatusRekomendasiRestService {
    StatusRekomendasi createStatusRekomendasi(StatusRekomendasi statusRekomendasi);

    StatusRekomendasi getById(int idStatus);

    List<StatusRekomendasi> getAll();
}
