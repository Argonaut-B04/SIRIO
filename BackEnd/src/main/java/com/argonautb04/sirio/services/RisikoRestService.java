package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Risiko;
import com.argonautb04.sirio.rest.RisikoDTO;

import java.util.List;

public interface RisikoRestService {

    /**
     * fungsi untuk membuat riisko baru
     *
     * @param risiko
     * @return menyimpan risiko ke dalam database
     */
    Risiko buatRisiko(Risiko risiko);

    /**
     * fungsi untuk mengambil risiko dengan id tertentu
     *
     * @param idRisiko
     * @return risiko dengan id tertentu
     */
    Risiko getById(int idRisiko);

    /**
     * fungsi untuk mengambil list semua risiko yang berstatus aktif
     *
     * @return list risiko berstatus aktif
     */
    List<Risiko> getAll();

    /**
     * fungsi untuk mengambil list semua risiko berdasarkan kategori tertentu
     *
     * @param kategori
     * @return list risiko dengan kategori tertentu
     */
    List<Risiko> getByKategori(Integer kategori);

    /**
     * fungsi untuk mengubah data risiko
     *
     * @param idRisiko
     * @param risiko
     * @return menyimpan perubahan data ke database
     */
    Risiko ubahRisiko(int idRisiko, Risiko risiko);

    /**
     * fungsi untuk menghapus risiko tertentu pada database
     *
     * @param idRisiko
     */
    void hapusRisiko(int idRisiko);

    /**
     * fungsi untuk menonaktifkan status risiko
     *
     * @param idRisiko
     * @return mengubah status risiko menjadi nonaktif pada database
     */
    Risiko nonaktifkanRisiko(int idRisiko);

    /**
     * Mengubah jenis object dari risikodto menjadi risiko
     *
     * @param risiko
     * @param risikoDTO
     * @return object risiko
     */
    Risiko transformasidto(Risiko risiko, RisikoDTO risikoDTO);

    /**
     * fungsi untuk mengecek apakah terdapat risiko tertentu pada database
     *
     * @param risiko
     * @return true apabila terdapat risiko, false jika tidak
     */
    boolean isExistInDatabase(Risiko risiko);

    /**
     * fungsi untuk mengubah parent dari risiko
     *
     * @param risikoAwal
     * @param risikoBaru
     * @return menyimpan pada database perubahan parent
     */
    RisikoDTO ubahHierarki(Risiko risikoAwal, RisikoDTO risikoBaru);

    /**
     * fungsi untuk mengecek apakah terdapat risiko dengan id tertentu
     *
     * @param idRisiko
     * @return risiko dengan id tertentu
     */
    Risiko validateExistById(int idRisiko);
}
