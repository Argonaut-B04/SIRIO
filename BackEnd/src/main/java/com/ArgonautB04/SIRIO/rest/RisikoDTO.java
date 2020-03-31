package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.model.StatusRisiko;

import java.util.List;

public class RisikoDTO {
    private int id;
    private String nama;
    private String kategori;
    private String komponen;
    private int parent;
    private int grantParent;
    private List<Risiko> child;
    private int sop;
    private String namaSop;
    private String linkSop;

    public int getGrantParent() {
        return grantParent;
    }

    public void setGrantParent(int grantParent) {
        this.grantParent = grantParent;
    }

    public String getNamaSop() {
        return namaSop;
    }

    public void setNamaSop(String namaSop) {
        this.namaSop = namaSop;
    }

    public String getLinkSop() {
        return linkSop;
    }

    public void setLinkSop(String linkSop) {
        this.linkSop = linkSop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKomponen() {
        return komponen;
    }

    public void setKomponen(String komponen) {
        this.komponen = komponen;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<Risiko> getChild() {
        return child;
    }

    public void setChild(List<Risiko> child) {
        this.child = child;
    }

    public int getSop() {
        return sop;
    }

    public void setSop(int sop) {
        this.sop = sop;
    }
}
