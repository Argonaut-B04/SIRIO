package com.ArgonautB04.SIRIO.rest;

import java.util.List;

public class HasilPemeriksaanDTO {
    private Integer status;
    private Integer id;
    private Integer idPembuat;
    private Integer idTugasPemeriksaan;
    private List<KomponenPemeriksaanDTO> daftarKomponenPemeriksaan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTugasPemeriksaan() {
        return idTugasPemeriksaan;
    }

    public void setIdTugasPemeriksaan(Integer idTugasPemeriksaan) {
        this.idTugasPemeriksaan = idTugasPemeriksaan;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public List<KomponenPemeriksaanDTO> getDaftarKomponenPemeriksaan() {
        return daftarKomponenPemeriksaan;
    }

    public void setDaftarKomponenPemeriksaan(List<KomponenPemeriksaanDTO> daftarKomponenPemeriksaan) {
        this.daftarKomponenPemeriksaan = daftarKomponenPemeriksaan;
    }
}
