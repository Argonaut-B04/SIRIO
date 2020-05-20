package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.HasilPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.TugasPemeriksaan;

import java.time.LocalDate;
import java.util.List;

public class DashboardKantorDTO {
    private Integer id;
    private Integer riskScore;
    private Integer jumlahTemuan;
    private Integer jumlahPemeriksaan;
    private Integer jumlahRekomendasi;
    private Double jumlahRekomendasiOverdue;
    private Double jumlahRekomendasiImplemented;
    private Double jumlahRekomendasiNotImplemented;
    private List<Integer> jumlahTemuanPerBulan;
    private List<Integer> jumlahRekomendasiOverduePerBulan;
    private List<Integer> jumlahRekomendasiImplementedPerBulan;
    private List<Integer> jumlahRekomendasiNotImplementedPerBulan;
    private List<String> daftarBulan;

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

    public Double getJumlahRekomendasiOverdue() {
        return jumlahRekomendasiOverdue;
    }

    public void setJumlahRekomendasiOverdue(Double jumlahRekomendasiOverdue) {
        this.jumlahRekomendasiOverdue = jumlahRekomendasiOverdue;
    }

    public Double getJumlahRekomendasiImplemented() {
        return jumlahRekomendasiImplemented;
    }

    public void setJumlahRekomendasiImplemented(Double jumlahRekomendasiImplemented) {
        this.jumlahRekomendasiImplemented = jumlahRekomendasiImplemented;
    }

    public Double getJumlahRekomendasiNotImplemented() {
        return jumlahRekomendasiNotImplemented;
    }

    public void setJumlahRekomendasiNotImplemented(Double jumlahRekomendasiNotImplemented) {
        this.jumlahRekomendasiNotImplemented = jumlahRekomendasiNotImplemented;
    }

    public List<Integer> getJumlahTemuanPerBulan() {
        return jumlahTemuanPerBulan;
    }

    public void setJumlahTemuanPerBulan(List<Integer> jumlahTemuanPerBulan) {
        this.jumlahTemuanPerBulan = jumlahTemuanPerBulan;
    }

    public List<Integer> getJumlahRekomendasiOverduePerBulan() {
        return jumlahRekomendasiOverduePerBulan;
    }

    public void setJumlahRekomendasiOverduePerBulan(List<Integer> jumlahRekomendasiOverduePerBulan) {
        this.jumlahRekomendasiOverduePerBulan = jumlahRekomendasiOverduePerBulan;
    }

    public List<Integer> getJumlahRekomendasiImplementedPerBulan() {
        return jumlahRekomendasiImplementedPerBulan;
    }

    public void setJumlahRekomendasiImplementedPerBulan(List<Integer> jumlahRekomendasiImplementedPerBulan) {
        this.jumlahRekomendasiImplementedPerBulan = jumlahRekomendasiImplementedPerBulan;
    }

    public List<Integer> getJumlahRekomendasiNotImplementedPerBulan() {
        return jumlahRekomendasiNotImplementedPerBulan;
    }

    public void setJumlahRekomendasiNotImplementedPerBulan(List<Integer> jumlahRekomendasiNotImplementedPerBulan) {
        this.jumlahRekomendasiNotImplementedPerBulan = jumlahRekomendasiNotImplementedPerBulan;
    }

    public List<String> getDaftarBulan() {
        return daftarBulan;
    }

    public void setDaftarBulan(List<String> daftarBulan) {
        this.daftarBulan = daftarBulan;
    }

    //    private Integer id;
//    private List<Integer> listTemuan;
//    private Integer jumlahTemuan;
//    private Integer jumlahRekomendasi;
//    private List<Integer> listRekomendasiDiimplementasi;
//    private Float jumlahRekomendasiDiimplementasi;
//    private List<Integer> listRekomendasiBelumDiimplementasi;
//    private Float jumlahRekomendasiBelumDiimplementasi;
//    private List<Integer> listRekomendasiOverdue;
//    private Float jumlahRekomendasiOverdue;
//    private List<String> listMonth;

}
