package com.argonautb04.sirio.rest;

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

    public void setUsernameQA(final String usernameQA) {
        this.usernameQA = usernameQA;
    }

    public Integer getIdHasilPemeriksaan() {
        return idHasilPemeriksaan;
    }

    public void setIdHasilPemeriksaan(final Integer idHasilPemeriksaan) {
        this.idHasilPemeriksaan = idHasilPemeriksaan;
    }

    public String getNamaQA() {
        return namaQA;
    }

    public void setNamaQA(final String namaQA) {
        this.namaQA = namaQA;
    }

    public String getNamaKantorCabang() {
        return namaKantorCabang;
    }

    public void setNamaKantorCabang(final String namaKantorCabang) {
        this.namaKantorCabang = namaKantorCabang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(final String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(final String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public Integer getIdQA() {
        return idQA;
    }

    public void setIdQA(final Integer idQA) {
        this.idQA = idQA;
    }

    public Integer getKantorCabang() {
        return kantorCabang;
    }

    public void setKantorCabang(final Integer kantorCabang) {
        this.kantorCabang = kantorCabang;
    }
}
