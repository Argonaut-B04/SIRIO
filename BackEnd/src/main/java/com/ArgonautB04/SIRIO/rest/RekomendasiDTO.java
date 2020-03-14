package com.ArgonautB04.SIRIO.rest;

public class RekomendasiDTO {
    private Integer id;
    private Integer idPembuat;
    private Integer status;
    private String keterangan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
