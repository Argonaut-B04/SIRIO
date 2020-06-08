package com.argonautb04.sirio.rest;

import java.util.List;

public class HasilPemeriksaanDTO {
    private Integer idStatus;
    private Integer id;
    private List<KomponenPemeriksaanDTO> daftarKomponenPemeriksaan;
    private TugasPemeriksaanDTO tugasPemeriksaan;
    private String namaStatus;
    private String namaPembuat;
    private String namaPemeriksa;
    private String usernamePembuat;
    private String feedback;
    private Boolean siapDijalankan;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }

    public TugasPemeriksaanDTO getTugasPemeriksaan() {
        return tugasPemeriksaan;
    }

    public void setTugasPemeriksaan(final TugasPemeriksaanDTO tugasPemeriksaan) {
        this.tugasPemeriksaan = tugasPemeriksaan;
    }

    public String getUsernamePembuat() {
        return usernamePembuat;
    }

    public void setUsernamePembuat(final String usernamePembuat) {
        this.usernamePembuat = usernamePembuat;
    }

    public String getNamaPemeriksa() {
        return namaPemeriksa;
    }

    public void setNamaPemeriksa(final String namaPemeriksa) {
        this.namaPemeriksa = namaPemeriksa;
    }

    public String getNamaPembuat() {
        return namaPembuat;
    }

    public void setNamaPembuat(final String namaPembuat) {
        this.namaPembuat = namaPembuat;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(final Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Boolean getSiapDijalankan() {
        return siapDijalankan;
    }

    public void setSiapDijalankan(final Boolean siapDijalankan) {
        this.siapDijalankan = siapDijalankan;
    }

    public String getNamaStatus() {
        return namaStatus;
    }

    public void setNamaStatus(final String namaStatus) {
        this.namaStatus = namaStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public List<KomponenPemeriksaanDTO> getDaftarKomponenPemeriksaan() {
        return daftarKomponenPemeriksaan;
    }

    public void setDaftarKomponenPemeriksaan(final List<KomponenPemeriksaanDTO> daftarKomponenPemeriksaan) {
        this.daftarKomponenPemeriksaan = daftarKomponenPemeriksaan;
    }
}
