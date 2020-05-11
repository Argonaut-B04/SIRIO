package com.ArgonautB04.SIRIO.rest;

import java.util.List;

public class DashboardDTO {

    private Integer id;
    private Integer jumlahTemuan;
    private Integer jumlahRekomendasi;
    private Integer jumlahRekomendasiDiimplementasi;
    private Integer jumlahRekomendasiBelumDiimplementasi;
    private Integer jumlahRekomendasiOverdue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJumlahTemuan() {
        return jumlahTemuan;
    }

    public void setJumlahTemuan(Integer jumlahTemuan) {
        this.jumlahTemuan = jumlahTemuan;
    }

    public Integer getJumlahRekomendasi() {
        return jumlahRekomendasi;
    }

    public void setJumlahRekomendasi(Integer jumlahRekomendasi) {
        this.jumlahRekomendasi = jumlahRekomendasi;
    }

    public Integer getJumlahRekomendasiDiimplementasi() {
        return jumlahRekomendasiDiimplementasi;
    }

    public void setJumlahRekomendasiDiimplementasi(Integer jumlahRekomendasiDiimplementasi) {
        this.jumlahRekomendasiDiimplementasi = jumlahRekomendasiDiimplementasi;
    }

    public Integer getJumlahRekomendasiBelumDiimplementasi() {
        return jumlahRekomendasiBelumDiimplementasi;
    }

    public void setJumlahRekomendasiBelumDiimplementasi(Integer jumlahRekomendasiBelumDiimplementasi) {
        this.jumlahRekomendasiBelumDiimplementasi = jumlahRekomendasiBelumDiimplementasi;
    }

    public Integer getJumlahRekomendasiOverdue() {
        return jumlahRekomendasiOverdue;
    }

    public void setJumlahRekomendasiOverdue(Integer jumlahRekomendasiOverdue) {
        this.jumlahRekomendasiOverdue = jumlahRekomendasiOverdue;
    }
}
