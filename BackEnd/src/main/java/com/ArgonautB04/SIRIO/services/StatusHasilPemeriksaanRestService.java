package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusHasilPemeriksaan;

import java.util.List;

public interface StatusHasilPemeriksaanRestService {
    StatusHasilPemeriksaan createStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan);

    StatusHasilPemeriksaan getById(int id_status);

    List<StatusHasilPemeriksaan> getAll();
}
