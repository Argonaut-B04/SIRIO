package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;

import java.util.List;

public interface RencanaPemeriksaanRestService {
    RencanaPemeriksaan buatRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan);

    RencanaPemeriksaan getById(int idRencanaPemeriksaan);

    List<RencanaPemeriksaan> getAll();

    RencanaPemeriksaan ubahRencanaPemeriksaan(int idRencanaPemeriksaan, RencanaPemeriksaan rencanaPemeriksaan);

    void hapusRencanaPemeriksaan(int idRencanaPemeriksaan);
}
