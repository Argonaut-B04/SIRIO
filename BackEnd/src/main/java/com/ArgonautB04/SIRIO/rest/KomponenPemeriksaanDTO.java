package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.TemuanRisiko;

import java.util.List;

public class KomponenPemeriksaanDTO {
    private Integer id;
    private Integer idRiskLevel;
    private Integer jumlahSampel;
    private String keteranganSampel;
    private List<TemuanRisikoDTO> daftarTemuanRisiko;
    private List<RekomendasiDTO> daftarRekomendasi;
    private List<TemuanRisiko> daftarTemuanRisikoTerdaftar;
    private List<Rekomendasi> daftarRekomendasiTerdaftar;
    private RisikoDTO risiko;

    public List<TemuanRisiko> getDaftarTemuanRisikoTerdaftar() {
        return daftarTemuanRisikoTerdaftar;
    }

    public void setDaftarTemuanRisikoTerdaftar(List<TemuanRisiko> daftarTemuanRisikoTerdaftar) {
        this.daftarTemuanRisikoTerdaftar = daftarTemuanRisikoTerdaftar;
    }

    public List<Rekomendasi> getDaftarRekomendasiTerdaftar() {
        return daftarRekomendasiTerdaftar;
    }

    public void setDaftarRekomendasiTerdaftar(List<Rekomendasi> daftarRekomendasiTerdaftar) {
        this.daftarRekomendasiTerdaftar = daftarRekomendasiTerdaftar;
    }

    public RisikoDTO getRisiko() {
        return risiko;
    }

    public void setRisiko(RisikoDTO risiko) {
        this.risiko = risiko;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRiskLevel() {
        return idRiskLevel;
    }

    public void setIdRiskLevel(Integer idRiskLevel) {
        this.idRiskLevel = idRiskLevel;
    }

    public Integer getJumlahSampel() {
        return jumlahSampel;
    }

    public void setJumlahSampel(Integer jumlahSampel) {
        this.jumlahSampel = jumlahSampel;
    }

    public String getKeteranganSampel() {
        return keteranganSampel;
    }

    public void setKeteranganSampel(String keteranganSampel) {
        this.keteranganSampel = keteranganSampel;
    }

    public List<TemuanRisikoDTO> getDaftarTemuanRisiko() {
        return daftarTemuanRisiko;
    }

    public void setDaftarTemuanRisiko(List<TemuanRisikoDTO> daftarTemuanRisiko) {
        this.daftarTemuanRisiko = daftarTemuanRisiko;
    }

    public List<RekomendasiDTO> getDaftarRekomendasi() {
        return daftarRekomendasi;
    }

    public void setDaftarRekomendasi(List<RekomendasiDTO> daftarRekomendasi) {
        this.daftarRekomendasi = daftarRekomendasi;
    }
}
