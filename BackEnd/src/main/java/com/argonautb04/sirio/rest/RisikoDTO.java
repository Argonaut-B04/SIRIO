package com.argonautb04.sirio.rest;

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

    public void setNamaParent(final String namaParent) {
        this.namaParent = namaParent;
    }

    public Integer getGrantParent() {
        return grantParent;
    }

    public void setGrantParent(final Integer grantParent) {
        this.grantParent = grantParent;
    }

    public String getNamaSop() {
        return namaSop;
    }

    public void setNamaSop(final String namaSop) {
        this.namaSop = namaSop;
    }

    public String getLinkSop() {
        return linkSop;
    }

    public void setLinkSop(final String linkSop) {
        this.linkSop = linkSop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(final String nama) {
        this.nama = nama;
    }

    public Integer getKategori() {
        return kategori;
    }

    public void setKategori(final Integer kategori) {
        this.kategori = kategori;
    }

    public String getDetailUraian() {
        return detailUraian;
    }

    public void setDetailUraian(final String detailUraian) {
        this.detailUraian = detailUraian;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(final String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getMetodologi() {
        return metodologi;
    }

    public void setMetodologi(final String metodologi) {
        this.metodologi = metodologi;
    }

    public String getKetentuanSampel() {
        return ketentuanSampel;
    }

    public void setKetentuanSampel(final String ketentuanSampel) {
        this.ketentuanSampel = ketentuanSampel;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(final Integer parent) {
        this.parent = parent;
    }

    public List<RisikoDTO> getChild() {
        return child;
    }

    public void setChild(final List<RisikoDTO> child) {
        this.child = child;
    }

    public Integer getSop() {
        return sop;
    }

    public void setSop(final Integer sop) {
        this.sop = sop;
    }
}
