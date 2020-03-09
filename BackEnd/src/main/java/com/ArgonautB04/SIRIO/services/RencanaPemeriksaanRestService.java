package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;

import java.util.List;

public interface RencanaPemeriksaanRestService {
    RencanaPemeriksaan createRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan);

    RencanaPemeriksaan getById(int id_rencana_pemeriksaan);

    List<RencanaPemeriksaan> getAll();

    RencanaPemeriksaan updateRencanaPemeriksaan(int id_rencana_pemeriksaan, RencanaPemeriksaan rencanaPemeriksaan);

    void deleteRencanaPemeriksaan(int id_rencana_pemeriksaan);
}
