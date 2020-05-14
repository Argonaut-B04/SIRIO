package com.ArgonautB04.SIRIO.rest;

import java.util.List;

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
    private String bulanTahun;
    private List<Integer> jumlahTemuanPerBulan;
    private List<Integer> jumlahRekomendasiOverduePerBulan;
    private List<Integer> jumlahRekomendasiImplementedPerBulan;
    private List<Integer> jumlahRekomendasiNotImplementedPerBulan;

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

//    public List<Integer> getListTemuan() {
//        return listTemuan;
//    }
//
//    public void setListTemuan(List<Integer> listTemuan) {
//        this.listTemuan = listTemuan;
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

    public String getBulanTahun() {
        return bulanTahun;
    }

    public void setBulanTahun(String bulanTahun) {
        this.bulanTahun = bulanTahun;
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
}

//    public List<Integer> getListRekomendasiDiimplementasi() {
//        return listRekomendasiDiimplementasi;
//    }
//
//    public void setListRekomendasiDiimplementasi(List<Integer> listRekomendasiDiimplementasi) {
//        this.listRekomendasiDiimplementasi = listRekomendasiDiimplementasi;
//    }
//
//    public Float getJumlahRekomendasiDiimplementasi() {
//        return jumlahRekomendasiDiimplementasi;
//    }
//
//    public void setJumlahRekomendasiDiimplementasi(Float jumlahRekomendasiDiimplementasi) {
//        this.jumlahRekomendasiDiimplementasi = jumlahRekomendasiDiimplementasi;
//    }
//
//    public List<Integer> getListRekomendasiBelumDiimplementasi() {
//        return listRekomendasiBelumDiimplementasi;
//    }
//
//    public void setListRekomendasiBelumDiimplementasi(List<Integer> listRekomendasiBelumDiimplementasi) {
//        this.listRekomendasiBelumDiimplementasi = listRekomendasiBelumDiimplementasi;
//    }
//
//    public Float getJumlahRekomendasiBelumDiimplementasi() {
//        return jumlahRekomendasiBelumDiimplementasi;
//    }
//
//    public void setJumlahRekomendasiBelumDiimplementasi(Float jumlahRekomendasiBelumDiimplementasi) {
//        this.jumlahRekomendasiBelumDiimplementasi = jumlahRekomendasiBelumDiimplementasi;
//    }
//
//    public List<Integer> getListRekomendasiOverdue() {
//        return listRekomendasiOverdue;
//    }
//
//    public void setListRekomendasiOverdue(List<Integer> listRekomendasiOverdue) {
//        this.listRekomendasiOverdue = listRekomendasiOverdue;
//    }
//
//    public Float getJumlahRekomendasiOverdue() {
//        return jumlahRekomendasiOverdue;
//    }
//
//    public void setJumlahRekomendasiOverdue(Float jumlahRekomendasiOverdue) {
//        this.jumlahRekomendasiOverdue = jumlahRekomendasiOverdue;
//    }
//
//    public List<String> getListMonth() {
//        return listMonth;
//    }
//
//    public void setListMonth(List<String> listMonth) {
//        this.listMonth = listMonth;
//    }
//}
