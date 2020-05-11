package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RekomendasiRestService {
    Rekomendasi buatRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int idRekomendasi);

    Optional<Rekomendasi> getOptionalById(int idRekomendasi);

    List<Rekomendasi> getAll();

    List<Rekomendasi> getByPembuat(int idQa);

//    List<Integer> getAllByMonth();

    List<Integer> getRekomendasiByMonth(List<Rekomendasi> rekomendasiList);

    List<Rekomendasi> getRekomendasiDiimplementasi();

    List<Rekomendasi> getRekomendasiDiimplementasiByPembuat(int idQa);

    List<Rekomendasi> getRekomendasiOverdue();

    List<Rekomendasi> getRekomendasiOverdueByPembuat(int idQa);

    List<Rekomendasi> getRekomendasiBelumDiimplementasi();

    List<Rekomendasi> getRekomendasiBelumDiimplementasiByPembuat(int idQa);

    List<String> getListMonth();

    List<Rekomendasi> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);
  
    List<Rekomendasi> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList);

    void ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi);

    void hapusRekomendasi(int idRekomendasi);

    List<Rekomendasi> getByPembuat(Employee pembuat);

    Rekomendasi validateExistInById(int idRekomendasi);

    void validateDateInputMoreThanToday(LocalDate localDate);

    void validateDeadlineCanBeSet(Rekomendasi rekomendasi);

    Rekomendasi buatAtauSimpanPerubahanRekomendasi(Rekomendasi rekomendasi, boolean tenggatWaktuTerubah);
}
