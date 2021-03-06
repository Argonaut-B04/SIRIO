package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Rekomendasi;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RekomendasiRestService {
    Rekomendasi buatRekomendasi(Rekomendasi rekomendasi);

    Rekomendasi getById(int idRekomendasi);

    Optional<Rekomendasi> getOptionalById(int idRekomendasi);

    List<Rekomendasi> getAll();

    List<Integer> getRekomendasiPerMonth(List<Rekomendasi> rekomendasiList);

    List<Integer> getRekomendasiPerMonthFiltered(List<Rekomendasi> rekomendasiList, LocalDate awal, LocalDate akhir);

    /**
     * fungsi untuk mengambil semua rekomendasi berdasarkan status sedang dijalankan
     * dan selesai
     *
     * @return list rekomendasi dengan status tertentu
     */
    List<Rekomendasi> getAllByStatus();

    /**
     * fungsi untuk mengambil semua rekomendasi berdasarkan range tanggal awal dan
     * tanggal akhir
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi
     */
    List<Rekomendasi> getAll(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil list rekomendasi berdasarkan pembuat
     *
     * @param idQa
     * @return list rekomendasi dengan pembuat tertentu
     */
    List<Rekomendasi> getByPembuat(int idQa);

    /**
     * fungsi untuk mengambil list rekomendasi berdasarkan pembuat dan range tanggal
     * tertentu
     *
     * @param idQa
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi
     */
    List<Rekomendasi> getByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengelompokkan rekomendasi per-bulannya berdasarkan range waktu
     * tertentu
     *
     * @param rekomendasiList
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list jumlah rekomendasi per-bulan
     */
    List<Integer> getRekomendasiByMonth(List<Rekomendasi> rekomendasiList, LocalDate tanggalAwal,
                                        LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil rekomendasi yang diimplementasi baik pada range waktu
     * tertentu ataupun semua
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi yang diimplementasi
     */
    List<Rekomendasi> getRekomendasiDiimplementasi(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil rekomendasi yang diimplementasi berdasarkan pembuatnya
     *
     * @param idQa
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi diimplementasi berdasarkan pembuat
     */
    List<Rekomendasi> getRekomendasiDiimplementasiByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil rekomendasi yang overdue baik pada range waktu
     * tertentu ataupun semua
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi yang overdue
     */
    List<Rekomendasi> getRekomendasiOverdue(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil rekomendasi yang overdue berdasarkan pembuatnya
     *
     * @param idQa
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi overdue berdasarkan pembuat
     */
    List<Rekomendasi> getRekomendasiOverdueByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil rekomendasi yang belum diimplementasi baik pada range
     * waktu tertentu ataupun semua
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi yang belum diimplementasi
     */
    List<Rekomendasi> getRekomendasiBelumDiimplementasi(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil rekomendasi yang belum diimplementasi berdasarkan
     * pembuatnya
     *
     * @param idQa
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list rekomendasi belum diimplementasi berdasarkan pembuat
     */
    List<Rekomendasi> getRekomendasiBelumDiimplementasiByPembuat(int idQa, LocalDate tanggalAwal,
                                                                 LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil list bulan pada range tanggal tertentu
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list bulan pada range tanggal tertentu
     */
    List<String> getListMonth(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<Rekomendasi> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    List<Rekomendasi> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList);

    List<Rekomendasi> getByDaftarKomponenPemeriksaanAndTenggatWaktu(List<KomponenPemeriksaan> komponenPemeriksaanList,
                                                                    LocalDate tenggatWaktu, LocalDate tenggatWaktu2);

    void ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi);

    void hapusRekomendasi(int idRekomendasi);

    List<Rekomendasi> getByPembuat(Employee pembuat);

    Rekomendasi validateExistInById(int idRekomendasi);

    void validateDateInputMoreThanToday(LocalDate localDate);

    void validateDeadlineCanBeSet(Rekomendasi rekomendasi);

    Rekomendasi buatAtauSimpanPerubahanRekomendasi(Rekomendasi rekomendasi, boolean tenggatWaktuTerubah);

    void jalankan(Rekomendasi rekomendasi);
}
