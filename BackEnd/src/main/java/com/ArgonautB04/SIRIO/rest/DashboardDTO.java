package com.ArgonautB04.SIRIO.rest;

public class DashboardDTO {
    private Integer id;
    private Integer riskScore;
    private Integer jumlahTemuan;
    private Integer jumlahPemeriksaan;
    private Integer jumlahRekomendasi;
    private Integer jumlahRekomendasiOverdue;
    private Integer jumlahRekomendasiImplemented;
    private Integer jumlahRekomendasiNotImplemented;
    private Float persenRekomendasiOverdue;
    private Float persenRekomendasiImplemented;
    private Float persenRekomendasiNotImplemented;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public Integer getJumlahTemuan() {
        return jumlahTemuan;
    }

    public void setJumlahTemuan(Integer jumlahTemuan) {
        this.jumlahTemuan = jumlahTemuan;
    }

    public Integer getJumlahPemeriksaan() {
        return jumlahPemeriksaan;
    }

    public void setJumlahPemeriksaan(Integer jumlahPemeriksaan) {
        this.jumlahPemeriksaan = jumlahPemeriksaan;
    }

    public Integer getJumlahRekomendasi() {
        return jumlahRekomendasi;
    }

    public void setJumlahRekomendasi(Integer jumlahRekomendasi) {
        this.jumlahRekomendasi = jumlahRekomendasi;
    }

    public Integer getJumlahRekomendasiOverdue() {
        return jumlahRekomendasiOverdue;
    }

    public void setJumlahRekomendasiOverdue(Integer jumlahRekomendasiOverdue) {
        this.jumlahRekomendasiOverdue = jumlahRekomendasiOverdue;
    }

    public Integer getJumlahRekomendasiImplemented() {
        return jumlahRekomendasiImplemented;
    }

    public void setJumlahRekomendasiImplemented(Integer jumlahRekomendasiImplemented) {
        this.jumlahRekomendasiImplemented = jumlahRekomendasiImplemented;
    }

    public Integer getJumlahRekomendasiNotImplemented() {
        return jumlahRekomendasiNotImplemented;
    }

    public void setJumlahRekomendasiNotImplemented(Integer jumlahRekomendasiNotImplemented) {
        this.jumlahRekomendasiNotImplemented = jumlahRekomendasiNotImplemented;
    }

    public Float getPersenRekomendasiOverdue() {
        return persenRekomendasiOverdue;
    }

    public void setPersenRekomendasiOverdue(Float persenRekomendasiOverdue) {
        this.persenRekomendasiOverdue = persenRekomendasiOverdue;
    }

    public Float getPersenRekomendasiImplemented() {
        return persenRekomendasiImplemented;
    }

    public void setPersenRekomendasiImplemented(Float persenRekomendasiImplemented) {
        this.persenRekomendasiImplemented = persenRekomendasiImplemented;
    }

    public Float getPersenRekomendasiNotImplemented() {
        return persenRekomendasiNotImplemented;
    }

    public void setPersenRekomendasiNotImplemented(Float persenRekomendasiNotImplemented) {
        this.persenRekomendasiNotImplemented = persenRekomendasiNotImplemented;
    }
}
