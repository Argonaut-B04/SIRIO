package com.argonautb04.sirio.rest;

public class BuktiPelaksanaanDTO {
    private Integer id;
    private String keterangan;
    private String lampiran;
    private String namaPembuat;
    private String namaPemeriksa;
    private Integer status;
    private String feedback;
    private Integer idRekomendasi;
    private String keteranganRekomendasi;
    private Integer statusRekomendasi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getLampiran() {
        return lampiran;
    }

    public void setLampiran(String lampiran) {
        this.lampiran = lampiran;
    }

    public String getNamaPembuat() {
        return namaPembuat;
    }

    public void setNamaPembuat(String namaPembuat) {
        this.namaPembuat = namaPembuat;
    }

    public Integer getIdRekomendasi() {
        return idRekomendasi;
    }

    public void setIdRekomendasi(Integer idRekomendasi) {
        this.idRekomendasi = idRekomendasi;
    }

    public String getNamaPemeriksa() {
        return namaPemeriksa;
    }

    public void setNamaPemeriksa(String namaPemeriksa) {
        this.namaPemeriksa = namaPemeriksa;
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

    public String getKeteranganRekomendasi() {
        return keteranganRekomendasi;
    }

    public void setKeteranganRekomendasi(String keteranganRekomendasi) {
        this.keteranganRekomendasi = keteranganRekomendasi;
    }

    public Integer getStatusRekomendasi() {
        return statusRekomendasi;
    }

    public void setStatusRekomendasi(Integer statusRekomendasi) {
        this.statusRekomendasi = statusRekomendasi;
    }
}
