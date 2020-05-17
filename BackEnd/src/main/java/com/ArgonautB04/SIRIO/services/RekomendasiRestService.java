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

    List<Rekomendasi> getAll(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getByPembuat(int idQa);

    List<Rekomendasi> getByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

//    List<Integer> getAllByMonth();

    List<Integer> getRekomendasiByMonth(List<Rekomendasi> rekomendasiList, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getRekomendasiDiimplementasi(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getRekomendasiDiimplementasiByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getRekomendasiOverdue(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getRekomendasiOverdueByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getRekomendasiBelumDiimplementasi(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getRekomendasiBelumDiimplementasiByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<String> getListMonth(LocalDate tanggalAwal, LocalDate tanggalAkhir);

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
