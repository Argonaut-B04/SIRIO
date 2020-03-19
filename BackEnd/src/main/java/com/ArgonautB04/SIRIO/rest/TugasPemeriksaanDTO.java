package com.ArgonautB04.SIRIO.rest;

public class TugasPemeriksaanDTO {
    private Integer id;
    private Integer idRencanaPemeriksaan;
    private String tanggalMulai;
    private String tanggalSelesai;
    private Integer idQA;
    private Integer kantorCabang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public Integer getIdRencanaPemeriksaan() {
        return idRencanaPemeriksaan;
    }

    public void setIdRencanaPemeriksaan(Integer idRencanaPemeriksaan) {
        this.idRencanaPemeriksaan = idRencanaPemeriksaan;
    }

    public Integer getIdQA() {
        return idQA;
    }

    public void setIdQA(Integer idQA) {
        this.idQA = idQA;
    }

    public Integer getKantorCabang() {
        return kantorCabang;
    }

    public void setKantorCabang(Integer kantorCabang) {
        this.kantorCabang = kantorCabang;
    }
}
