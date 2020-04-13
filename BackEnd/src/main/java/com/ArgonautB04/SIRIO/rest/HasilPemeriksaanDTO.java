package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public TugasPemeriksaanDTO getTugasPemeriksaan() {
        return tugasPemeriksaan;
    }

    public void setTugasPemeriksaan(TugasPemeriksaanDTO tugasPemeriksaan) {
        this.tugasPemeriksaan = tugasPemeriksaan;
    }

    public String getUsernamePembuat() {
        return usernamePembuat;
    }

    public void setUsernamePembuat(String usernamePembuat) {
        this.usernamePembuat = usernamePembuat;
    }

    public String getNamaPemeriksa() {
        return namaPemeriksa;
    }

    public void setNamaPemeriksa(String namaPemeriksa) {
        this.namaPemeriksa = namaPemeriksa;
    }

    public String getNamaPembuat() {
        return namaPembuat;
    }

    public void setNamaPembuat(String namaPembuat) {
        this.namaPembuat = namaPembuat;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Boolean getSiapDijalankan() {
        return siapDijalankan;
    }

    public void setSiapDijalankan(Boolean siapDijalankan) {
        this.siapDijalankan = siapDijalankan;
    }

    public String getNamaStatus() {
        return namaStatus;
    }

    public void setNamaStatus(String namaStatus) {
        this.namaStatus = namaStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<KomponenPemeriksaanDTO> getDaftarKomponenPemeriksaan() {
        return daftarKomponenPemeriksaan;
    }

    public void setDaftarKomponenPemeriksaan(List<KomponenPemeriksaanDTO> daftarKomponenPemeriksaan) {
        this.daftarKomponenPemeriksaan = daftarKomponenPemeriksaan;
    }
}
