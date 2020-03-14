package com.ArgonautB04.SIRIO.rest;

import java.util.List;

public class KomponenPemeriksaanDTO {
    private Integer id;
    private Integer idRiskLevel;
    private Integer idRisiko;
    private Integer jumlahSampel;
    private String keteranganSampel;
    private List<TemuanRisikoDTO> daftarTemuanRisiko;
    private List<RekomendasiDTO> daftarRekomendasi;

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

    public Integer getIdRisiko() {
        return idRisiko;
    }

    public void setIdRisiko(Integer idRisiko) {
        this.idRisiko = idRisiko;
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
