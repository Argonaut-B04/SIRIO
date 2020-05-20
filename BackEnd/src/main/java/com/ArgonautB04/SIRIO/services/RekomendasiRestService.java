package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RekomendasiRestService {
    Rekomendasi buatRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int idRekomendasi);

    Optional<Rekomendasi> getOptionalById(int idRekomendasi);

    List<Rekomendasi> getAll();

    List<Integer> getAllByMonth();

    List<Integer> getRekomendasiByMonth(List<Rekomendasi> rekomendasiList, LocalDate awal, LocalDate akhir);

    List<Integer> getRekomendasiPerMonth(List<Rekomendasi> rekomendasiList);

    List<Integer> getRekomendasiPerMonthFiltered(List<Rekomendasi> rekomendasiList, LocalDate awal, LocalDate akhir);

    List<Rekomendasi> getRekomendasiDiimplementasi();

    List<Rekomendasi> getRekomendasiOverdue();

    List<Rekomendasi> getRekomendasiBelumDiimplementasi();

    List<String> getListMonth(LocalDate awal, LocalDate akhir);

    List<Rekomendasi> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);
  
    List<Rekomendasi> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList);

    List<Rekomendasi> getByDaftarKomponenPemeriksaanAndTenggatWaktu(
            List<KomponenPemeriksaan> komponenPemeriksaanList, LocalDate tenggatWaktu, LocalDate tenggatWaktu2);

    void ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi);

    void hapusRekomendasi(int idRekomendasi);

    List<Rekomendasi> getByPembuat(Employee pembuat);

    Rekomendasi validateExistInById(int idRekomendasi);

    void validateDateInputMoreThanToday(LocalDate localDate);

    void validateDeadlineCanBeSet(Rekomendasi rekomendasi);

    Rekomendasi buatAtauSimpanPerubahanRekomendasi(Rekomendasi rekomendasi, boolean tenggatWaktuTerubah);
}
