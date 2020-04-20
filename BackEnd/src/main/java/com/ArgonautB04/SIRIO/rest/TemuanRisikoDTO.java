package com.ArgonautB04.SIRIO.rest;

public class TemuanRisikoDTO {
    private Integer id;
    private Integer idPembuat;
    private String keterangan;
    private Integer idRisiko;

    public Integer getIdRisiko() {
        return idRisiko;
    }

    public void setIdRisiko(Integer idRisiko) {
        this.idRisiko = idRisiko;
    }

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
}
