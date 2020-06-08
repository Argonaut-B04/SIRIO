package com.argonautb04.sirio.rest;

public class TemuanRisikoDTO {
    private Integer id;
    private Integer idPembuat;
    private String keterangan;
    private Integer idRisiko;

    public Integer getIdRisiko() {
        return idRisiko;
    }

    public void setIdRisiko(final Integer idRisiko) {
        this.idRisiko = idRisiko;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(final Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(final String keterangan) {
        this.keterangan = keterangan;
    }
}
