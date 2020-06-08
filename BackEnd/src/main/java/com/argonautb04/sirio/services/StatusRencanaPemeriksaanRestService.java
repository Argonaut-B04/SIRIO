package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.StatusRencanaPemeriksaan;

import java.util.List;

public interface StatusRencanaPemeriksaanRestService {
    StatusRencanaPemeriksaan buatStatusRencanaPemeriksaan(StatusRencanaPemeriksaan statusRencanaPemeriksaan);

    StatusRencanaPemeriksaan getById(int idStatus);

    List<StatusRencanaPemeriksaan> getAll();
}
