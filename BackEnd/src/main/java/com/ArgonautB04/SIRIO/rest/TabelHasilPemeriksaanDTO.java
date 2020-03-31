package com.ArgonautB04.SIRIO.rest;

public class TabelHasilPemeriksaanDTO {
    private Integer id;
    private Integer idTugasPemeriksaan;
    private String kantorCabang;
    private String status;
    private Boolean siapDijalankan;

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

    public String getKantorCabang() {
        return kantorCabang;
    }

    public void setKantorCabang(String kantorCabang) {
        this.kantorCabang = kantorCabang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSiapDijalankan() {
        return siapDijalankan;
    }

    public void setSiapDijalankan(Boolean siapDijalankan) {
        this.siapDijalankan = siapDijalankan;
    }
}
