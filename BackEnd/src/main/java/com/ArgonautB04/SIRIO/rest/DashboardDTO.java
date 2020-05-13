package com.ArgonautB04.SIRIO.rest;

import java.util.List;

public class DashboardDTO {

    private Integer id;
    private List<Integer> listTemuan;
    private Integer jumlahTemuan;
    private Integer jumlahRekomendasi;
    private List<Integer> listRekomendasiDiimplementasi;
    private Float jumlahRekomendasiDiimplementasi;
    private List<Integer> listRekomendasiBelumDiimplementasi;
    private Float jumlahRekomendasiBelumDiimplementasi;
    private List<Integer> listRekomendasiOverdue;
    private Float jumlahRekomendasiOverdue;
    private List<String> listMonth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getListTemuan() {
        return listTemuan;
    }

    public void setListTemuan(List<Integer> listTemuan) {
        this.listTemuan = listTemuan;
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

    public List<Integer> getListRekomendasiDiimplementasi() {
        return listRekomendasiDiimplementasi;
    }

    public void setListRekomendasiDiimplementasi(List<Integer> listRekomendasiDiimplementasi) {
        this.listRekomendasiDiimplementasi = listRekomendasiDiimplementasi;
    }

    public Float getJumlahRekomendasiDiimplementasi() {
        return jumlahRekomendasiDiimplementasi;
    }

    public void setJumlahRekomendasiDiimplementasi(Float jumlahRekomendasiDiimplementasi) {
        this.jumlahRekomendasiDiimplementasi = jumlahRekomendasiDiimplementasi;
    }

    public List<Integer> getListRekomendasiBelumDiimplementasi() {
        return listRekomendasiBelumDiimplementasi;
    }

    public void setListRekomendasiBelumDiimplementasi(List<Integer> listRekomendasiBelumDiimplementasi) {
        this.listRekomendasiBelumDiimplementasi = listRekomendasiBelumDiimplementasi;
    }

    public Float getJumlahRekomendasiBelumDiimplementasi() {
        return jumlahRekomendasiBelumDiimplementasi;
    }

    public void setJumlahRekomendasiBelumDiimplementasi(Float jumlahRekomendasiBelumDiimplementasi) {
        this.jumlahRekomendasiBelumDiimplementasi = jumlahRekomendasiBelumDiimplementasi;
    }

    public List<Integer> getListRekomendasiOverdue() {
        return listRekomendasiOverdue;
    }

    public void setListRekomendasiOverdue(List<Integer> listRekomendasiOverdue) {
        this.listRekomendasiOverdue = listRekomendasiOverdue;
    }

    public Float getJumlahRekomendasiOverdue() {
        return jumlahRekomendasiOverdue;
    }

    public void setJumlahRekomendasiOverdue(Float jumlahRekomendasiOverdue) {
        this.jumlahRekomendasiOverdue = jumlahRekomendasiOverdue;
    }

    public List<String> getListMonth() {
        return listMonth;
    }

    public void setListMonth(List<String> listMonth) {
        this.listMonth = listMonth;
    }
}