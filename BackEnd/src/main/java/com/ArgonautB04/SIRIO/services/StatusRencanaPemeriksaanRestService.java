package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRencanaPemeriksaan;

public interface StatusRencanaPemeriksaanRestService {
    StatusRencanaPemeriksaan createStatusRencanaPemeriksaan(StatusRencanaPemeriksaan statusRencanaPemeriksaan);

    StatusRencanaPemeriksaan getById(int id_status);
}
