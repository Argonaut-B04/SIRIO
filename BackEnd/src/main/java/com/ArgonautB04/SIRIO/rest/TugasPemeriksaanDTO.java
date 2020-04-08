package com.ArgonautB04.SIRIO.rest;

public class TugasPemeriksaanDTO {
    private Integer id;
    private Integer idHasilPemeriksaan;
    private String tanggalMulai;
    private String tanggalSelesai;
    private Integer idQA;
    private Integer kantorCabang;
    private String namaKantorCabang;
    private String namaQA;
    private String usernameQA;

    public String getUsernameQA() {
        return usernameQA;
    }

    public void setUsernameQA(String usernameQA) {
        this.usernameQA = usernameQA;
    }

    public Integer getIdHasilPemeriksaan() {
        return idHasilPemeriksaan;
    }

    public void setIdHasilPemeriksaan(Integer idHasilPemeriksaan) {
        this.idHasilPemeriksaan = idHasilPemeriksaan;
    }

    public String getNamaQA() {
        return namaQA;
    }

    public void setNamaQA(String namaQA) {
        this.namaQA = namaQA;
    }

    public String getNamaKantorCabang() {
        return namaKantorCabang;
    }

    public void setNamaKantorCabang(String namaKantorCabang) {
        this.namaKantorCabang = namaKantorCabang;
    }

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
