package com.argonautb04.sirio.rest;

public class SOPDTO {
    private Integer id;
    private String judul;
    private String kategori;
    private String linkDokumen;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(final String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(final String kategori) {
        this.kategori = kategori;
    }

    public String getLinkDokumen() {
        return linkDokumen;
    }

    public void setLinkDokumen(final String linkDokumen) {
        this.linkDokumen = linkDokumen;
    }

}