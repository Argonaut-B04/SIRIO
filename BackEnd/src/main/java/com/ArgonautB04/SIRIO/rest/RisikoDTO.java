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
    private int status;
    private int parent;
    private List<RisikoDTO> child;
    private int sop;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<RisikoDTO> getChild() {
        return child;
    }

    public void setChild(List<RisikoDTO> child) {
        this.child = child;
    }

    public int getSop() {
        return sop;
    }

    public void setSop(int sop) {
        this.sop = sop;
    }
}
