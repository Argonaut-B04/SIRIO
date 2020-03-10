package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusHasilPemeriksaan;

import java.util.List;

public interface StatusHasilPemeriksaanRestService {
    StatusHasilPemeriksaan buatStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan);

    StatusHasilPemeriksaan getById(int idStatus);

    List<StatusHasilPemeriksaan> getAll();
}
