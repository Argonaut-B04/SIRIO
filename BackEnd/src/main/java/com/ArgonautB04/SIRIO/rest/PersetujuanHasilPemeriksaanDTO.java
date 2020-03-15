package com.ArgonautB04.SIRIO.rest;

public class PersetujuanHasilPemeriksaanDTO {
    private Integer idPemeriksa;
    private Integer idHasilPemeriksaan;
    private Integer status;
    private String feedback;

    public Integer getIdPemeriksa() {
        return idPemeriksa;
    }

    public void setIdPemeriksa(Integer idPemeriksa) {
        this.idPemeriksa = idPemeriksa;
    }

    public Integer getIdHasilPemeriksaan() {
        return idHasilPemeriksaan;
    }

    public void setIdHasilPemeriksaan(Integer idHasilPemeriksaan) {
        this.idHasilPemeriksaan = idHasilPemeriksaan;
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
