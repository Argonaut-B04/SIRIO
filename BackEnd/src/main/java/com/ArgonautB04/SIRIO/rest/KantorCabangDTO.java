package com.argonautb04.sirio.rest;

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

    public void setIdRiskRating(final Integer idRiskRating) {
        this.idRiskRating = idRiskRating;
    }

    public boolean isKunjunganAudit() {
        return kunjunganAudit;
    }

    public void setKunjunganAudit(final boolean kunjunganAudit) {
        this.kunjunganAudit = kunjunganAudit;
    }

    public Integer getIdPemilik() {
        return idPemilik;
    }

    public void setIdPemilik(final Integer idPemilik) {
        this.idPemilik = idPemilik;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(final String regional) {
        this.regional = regional;
    }

    public String getArea() {
        return area;
    }

    public void setArea(final String area) {
        this.area = area;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(final Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNamaKantorCabang() {
        return namaKantorCabang;
    }

    public void setNamaKantorCabang(final String namaKantorCabang) {
        this.namaKantorCabang = namaKantorCabang;
    }

}
