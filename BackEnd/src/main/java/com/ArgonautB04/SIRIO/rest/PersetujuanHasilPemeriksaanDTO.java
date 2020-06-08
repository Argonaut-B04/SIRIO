package com.argonautb04.sirio.rest;

public class PersetujuanHasilPemeriksaanDTO {
    private Integer idPemeriksa;
    private Integer id;
    private Integer status;
    private String feedback;

    public Integer getIdPemeriksa() {
        return idPemeriksa;
    }

    public void setIdPemeriksa(final Integer idPemeriksa) {
        this.idPemeriksa = idPemeriksa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }
}
