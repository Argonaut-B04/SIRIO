package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Risiko;
import com.argonautb04.sirio.model.TemuanRisiko;
import com.argonautb04.sirio.model.TugasPemeriksaan;

import java.time.LocalDate;
import java.util.List;

public interface TemuanRisikoRestService {
    TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko);

    TemuanRisiko getById(int idTemuanRisiko);

    List<TemuanRisiko> getAll();

    List<Integer> getTemuanPerMonth(List<TemuanRisiko> temuanRisikoList);

    List<Integer> getTemuanPerMonthFiltered(List<TemuanRisiko> temuanRisikoList, LocalDate awal, LocalDate akhir);

    /**
     * fungsi untuk mengambil semua temuan risiko berdasarkan range tanggal awal dan
     * tanggal akhir
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list temuan risiko
     */
    List<TemuanRisiko> getAll(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengambil list temuan risiko berdasarkan pembuat
     *
     * @param idQa
     * @return list temuan risiko dengan pembuat tertentu
     */
    List<TemuanRisiko> getByPembuat(int idQa);

    /**
     * fungsi untuk mengambil list temuan risiko berdasarkan pembuat dan range
     * tanggal tertentu
     *
     * @param idQa
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list temuan risiko
     */
    List<TemuanRisiko> getByPembuat(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengelompokkan temuan risiko per-bulan
     *
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list jumlah temuan risiko per-bulan
     */
    List<Integer> getAllByMonth(LocalDate tanggalAwal, LocalDate tanggalAkhir);

    /**
     * fungsi untuk mengelompokkan temuan risiko berdasarkan pembuatnya per-bulan
     *
     * @param idQa
     * @param tanggalAwal
     * @param tanggalAkhir
     * @return list jumlah temuan risiko berdasarkan pembuat per-bulan
     */
    List<Integer> getByPembuatByMonth(int idQa, LocalDate tanggalAwal, LocalDate tanggalAkhir);

    List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan);

    List<TemuanRisiko> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList);

    List<TemuanRisiko> getHistoriTemuanRisikoKantorCabang(TugasPemeriksaan tugasPemeriksaan, Risiko risiko);

    TemuanRisiko ubahTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko);

    void hapusTemuanRisiko(int idTemuanRisiko);
}
