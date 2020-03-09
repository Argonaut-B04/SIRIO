package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusHasilPemeriksaan;

public interface StatusHasilPemeriksaanRestService {
    StatusHasilPemeriksaan createStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan);

    StatusHasilPemeriksaan getById(int id_status);
}
