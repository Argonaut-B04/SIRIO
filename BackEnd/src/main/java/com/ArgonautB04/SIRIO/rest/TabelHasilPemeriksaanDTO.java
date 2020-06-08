package com.argonautb04.sirio.rest;

public class TabelHasilPemeriksaanDTO {
    private Integer id;
    private Integer idTugasPemeriksaan;
    private String kantorCabang;
    private String status;
    private Boolean siapDijalankan;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIdTugasPemeriksaan() {
        return idTugasPemeriksaan;
    }

    public void setIdTugasPemeriksaan(final Integer idTugasPemeriksaan) {
        this.idTugasPemeriksaan = idTugasPemeriksaan;
    }

    public String getKantorCabang() {
        return kantorCabang;
    }

    public void setKantorCabang(final String kantorCabang) {
        this.kantorCabang = kantorCabang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Boolean getSiapDijalankan() {
        return siapDijalankan;
    }

    public void setSiapDijalankan(final Boolean siapDijalankan) {
        this.siapDijalankan = siapDijalankan;
    }
}
