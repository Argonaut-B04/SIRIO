package com.ArgonautB04.SIRIO.rest;

public class PersetujuanHasilPemeriksaanDTO {
    private Integer idPemeriksa;
    private Integer id;
    private Integer status;
    private String feedback;

    public Integer getIdPemeriksa() {
        return idPemeriksa;
    }

    public void setIdPemeriksa(Integer idPemeriksa) {
        this.idPemeriksa = idPemeriksa;
    }

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
