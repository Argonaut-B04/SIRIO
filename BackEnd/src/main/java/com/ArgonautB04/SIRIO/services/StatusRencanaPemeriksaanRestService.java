package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRencanaPemeriksaan;

import java.util.List;

public interface StatusRencanaPemeriksaanRestService {
    StatusRencanaPemeriksaan createStatusRencanaPemeriksaan(StatusRencanaPemeriksaan statusRencanaPemeriksaan);

    StatusRencanaPemeriksaan getById(int idStatus);

    List<StatusRencanaPemeriksaan> getAll();
}
