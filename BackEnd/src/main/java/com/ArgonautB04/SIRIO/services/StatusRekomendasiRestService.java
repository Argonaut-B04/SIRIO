package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.StatusRekomendasi;

import java.util.List;

public interface StatusRekomendasiRestService {
    StatusRekomendasi buatStatusRekomendasi(StatusRekomendasi statusRekomendasi);

    StatusRekomendasi getById(int idStatus);

    List<StatusRekomendasi> getAll();

    StatusRekomendasi getByNamaStatus(String namaStatus);
}
