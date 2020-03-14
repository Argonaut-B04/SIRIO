package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KantorCabang;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RekomendasiRestService {
    Rekomendasi buatRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int idRekomendasi);

    List<Rekomendasi> getAll();

    List<Rekomendasi> getByKantorCabang(KantorCabang kantorCabang);

    Rekomendasi ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi);

    void hapusRekomendasi(int idRekomendasi);

    Rekomendasi ubahTenggatWaktu(int idRekomendasi, LocalDate tenggatWaktuLocalDate);

    List<Rekomendasi> getByPembuat(Employee pembuat);
}
