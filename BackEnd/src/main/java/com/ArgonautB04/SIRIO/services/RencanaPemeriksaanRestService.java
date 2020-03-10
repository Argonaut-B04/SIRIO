package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;

import java.util.List;

public interface RencanaPemeriksaanRestService {
    RencanaPemeriksaan createRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan);

    RencanaPemeriksaan getById(int idRencanaPemeriksaan);

    List<RencanaPemeriksaan> getAll();

    RencanaPemeriksaan updateRencanaPemeriksaan(int idRencanaPemeriksaan, RencanaPemeriksaan rencanaPemeriksaan);

    void deleteRencanaPemeriksaan(int idRencanaPemeriksaan);
}
