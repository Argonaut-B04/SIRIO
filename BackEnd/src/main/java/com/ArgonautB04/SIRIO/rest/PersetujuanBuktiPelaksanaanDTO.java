package com.argonautb04.sirio.rest;

public class PersetujuanBuktiPelaksanaanDTO {
    private Integer idPemeriksa;
    private Integer idBuktiPelaksanaan;
    private Integer status;
    private String feedback;
    // private Integer idRekomendasi;

    public Integer getIdPemeriksa() {
        return idPemeriksa;
    }

    public void setIdPemeriksa(final Integer idPemeriksa) {
        this.idPemeriksa = idPemeriksa;
    }

    public Integer getIdBuktiPelaksanaan() {
        return idBuktiPelaksanaan;
    }

    public void setIdBuktiPelaksanaan(final Integer idBuktiPelaksanaan) {
        this.idBuktiPelaksanaan = idBuktiPelaksanaan;
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

    // public Integer getIdRekomendasi() {
    // return idRekomendasi;
    // }
    //
    // public void setIdRekomendasi(Integer idRekomendasi) {
    // this.idRekomendasi = idRekomendasi;
    // }
}
