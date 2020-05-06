package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.model.Risiko;

import java.util.List;

public class RisikoDTO {
    private Integer id;
    private String nama;
    private Integer kategori;
    private String detailUraian;
    private String deskripsi;
    private String metodologi;
    private String ketentuanSampel;
    private Integer parent;
    private String namaParent;
    private Integer grantParent;
    private List<RisikoDTO> child;
    private Integer sop;
    private String namaSop;
    private String linkSop;

    public String getNamaParent() {
        return namaParent;
    }

    public void setNamaParent(String namaParent) {
        this.namaParent = namaParent;
    }

    public Integer getGrantParent() {
        return grantParent;
    }

    public void setGrantParent(Integer grantParent) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKategori() {
        return kategori;
    }

    public void setKategori(Integer kategori) {
        this.kategori = kategori;
    }

    public String getDetailUraian() {
        return detailUraian;
    }

    public void setDetailUraian(String detailUraian) {
        this.detailUraian = detailUraian;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getMetodologi() {
        return metodologi;
    }

    public void setMetodologi(String metodologi) {
        this.metodologi = metodologi;
    }

    public String getKetentuanSampel() {
        return ketentuanSampel;
    }

    public void setKetentuanSampel(String ketentuanSampel) {
        this.ketentuanSampel = ketentuanSampel;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public List<RisikoDTO> getChild() {
        return child;
    }

    public void setChild(List<RisikoDTO> child) {
        this.child = child;
    }

    public Integer getSop() {
        return sop;
    }

    public void setSop(Integer sop) {
        this.sop = sop;
    }
}
