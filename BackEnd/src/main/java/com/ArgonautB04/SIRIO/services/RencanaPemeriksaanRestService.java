package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.model.Employee;

import java.util.List;
import java.util.Optional;

public interface RencanaPemeriksaanRestService {
    RencanaPemeriksaan buatRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan);

    RencanaPemeriksaan getById(int idRencanaPemeriksaan);

    List<RencanaPemeriksaan> getAll();

    RencanaPemeriksaan ubahRencanaPemeriksaan(int idRencanaPemeriksaan, RencanaPemeriksaan rencanaPemeriksaan);

    void hapusRencanaPemeriksaan(int idRencanaPemeriksaan);

    List<RencanaPemeriksaan> getByPembuat(Employee pembuat);

    Optional<RencanaPemeriksaan> getByNama (String nama);

    RencanaPemeriksaan validateExistById(int idRencanaPemeriksaan);
}
