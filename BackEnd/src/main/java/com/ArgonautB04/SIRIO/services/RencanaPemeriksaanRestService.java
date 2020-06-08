package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.RencanaPemeriksaan;

import java.util.List;
import java.util.Optional;

public interface RencanaPemeriksaanRestService {
    RencanaPemeriksaan buatRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan);

    RencanaPemeriksaan getById(int idRencanaPemeriksaan);

    List<RencanaPemeriksaan> getAll();

    RencanaPemeriksaan ubahRencanaPemeriksaan(int idRencanaPemeriksaan, RencanaPemeriksaan rencanaPemeriksaan);

    void hapusRencanaPemeriksaan(int idRencanaPemeriksaan);

    List<RencanaPemeriksaan> getByPembuat(Employee pembuat);

    Optional<RencanaPemeriksaan> getByNama(String nama);

    RencanaPemeriksaan validateExistById(int idRencanaPemeriksaan);
}
