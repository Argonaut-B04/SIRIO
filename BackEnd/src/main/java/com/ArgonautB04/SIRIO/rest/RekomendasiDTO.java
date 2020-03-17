package com.ArgonautB04.SIRIO.rest;

public class RekomendasiDTO {
    private Integer id;
    private Integer idPembuat;
    private String keterangan;
    private String tenggatWaktu;
    private String durasi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTenggatWaktu() {
        return tenggatWaktu;
    }

    public void setTenggatWaktu(String tenggatWaktu) {
        this.tenggatWaktu = tenggatWaktu;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }
}
