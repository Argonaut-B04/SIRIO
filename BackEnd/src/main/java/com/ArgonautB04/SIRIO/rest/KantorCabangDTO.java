package com.ArgonautB04.SIRIO.rest;

public class KantorCabangDTO {
    private Integer id;
    private String namaKantorCabang;
    private Integer idPembuat;
    private Integer idPemilik;
    private Integer idRiskRating;
    private String regional;
    private String area;
    private boolean kunjunganAudit;

    public Integer getIdRiskRating() {
        return idRiskRating;
    }

    public void setIdRiskRating(Integer idRiskRating) {
        this.idRiskRating = idRiskRating;
    }

    public boolean isKunjunganAudit() {
        return kunjunganAudit;
    }

    public void setKunjunganAudit(boolean kunjunganAudit) {
        this.kunjunganAudit = kunjunganAudit;
    }

    public Integer getIdPemilik() {
        return idPemilik;
    }

    public void setIdPemilik(Integer idPemilik) {
        this.idPemilik = idPemilik;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaKantorCabang() {
        return namaKantorCabang;
    }

    public void setNamaKantorCabang(String namaKantorCabang) {
        this.namaKantorCabang = namaKantorCabang;
    }



}
