package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.StatusHasilPemeriksaan;

import java.util.List;

public interface StatusHasilPemeriksaanRestService {
    StatusHasilPemeriksaan buatStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan);

    StatusHasilPemeriksaan getById(int idStatus);

    List<StatusHasilPemeriksaan> getAll();
}
