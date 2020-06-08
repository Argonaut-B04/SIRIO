package com.argonautb04.sirio.rest;

import java.util.List;

public class KomponenPemeriksaanDTO {
    private Integer id;
    private Integer idRiskLevel;
    private Float bobotRiskLevel;
    private Integer jumlahSampel;
    private Integer jumlahPopulasi;
    private Integer jumlahSampelError;
    private String keteranganSampel;
    private String namaRiskLevel;
    private List<TemuanRisikoDTO> daftarTemuanRisiko;
    private List<RekomendasiDTO> daftarRekomendasi;
    private List<TemuanRisikoDTO> daftarTemuanRisikoTerdaftar;
    private List<RekomendasiDTO> daftarRekomendasiTerdaftar;
    private RisikoDTO risiko;

    public Integer getJumlahPopulasi() {
        return jumlahPopulasi;
    }

    public void setJumlahPopulasi(final Integer jumlahPopulasi) {
        this.jumlahPopulasi = jumlahPopulasi;
    }

    public Integer getJumlahSampelError() {
        return jumlahSampelError;
    }

    public void setJumlahSampelError(final Integer jumlahSampelError) {
        this.jumlahSampelError = jumlahSampelError;
    }

    public Float getBobotRiskLevel() {
        return bobotRiskLevel;
    }

    public void setBobotRiskLevel(final Float bobotRiskLevel) {
        this.bobotRiskLevel = bobotRiskLevel;
    }

    public String getNamaRiskLevel() {
        return namaRiskLevel;
    }

    public void setNamaRiskLevel(final String namaRiskLevel) {
        this.namaRiskLevel = namaRiskLevel;
    }

    public List<TemuanRisikoDTO> getDaftarTemuanRisikoTerdaftar() {
        return daftarTemuanRisikoTerdaftar;
    }

    public void setDaftarTemuanRisikoTerdaftar(final List<TemuanRisikoDTO> daftarTemuanRisikoTerdaftar) {
        this.daftarTemuanRisikoTerdaftar = daftarTemuanRisikoTerdaftar;
    }

    public List<RekomendasiDTO> getDaftarRekomendasiTerdaftar() {
        return daftarRekomendasiTerdaftar;
    }

    public void setDaftarRekomendasiTerdaftar(final List<RekomendasiDTO> daftarRekomendasiTerdaftar) {
        this.daftarRekomendasiTerdaftar = daftarRekomendasiTerdaftar;
    }

    public RisikoDTO getRisiko() {
        return risiko;
    }

    public void setRisiko(final RisikoDTO risiko) {
        this.risiko = risiko;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIdRiskLevel() {
        return idRiskLevel;
    }

    public void setIdRiskLevel(final Integer idRiskLevel) {
        this.idRiskLevel = idRiskLevel;
    }

    public Integer getJumlahSampel() {
        return jumlahSampel;
    }

    public void setJumlahSampel(final Integer jumlahSampel) {
        this.jumlahSampel = jumlahSampel;
    }

    public String getKeteranganSampel() {
        return keteranganSampel;
    }

    public void setKeteranganSampel(final String keteranganSampel) {
        this.keteranganSampel = keteranganSampel;
    }

    public List<TemuanRisikoDTO> getDaftarTemuanRisiko() {
        return daftarTemuanRisiko;
    }

    public void setDaftarTemuanRisiko(final List<TemuanRisikoDTO> daftarTemuanRisiko) {
        this.daftarTemuanRisiko = daftarTemuanRisiko;
    }

    public List<RekomendasiDTO> getDaftarRekomendasi() {
        return daftarRekomendasi;
    }

    public void setDaftarRekomendasi(final List<RekomendasiDTO> daftarRekomendasi) {
        this.daftarRekomendasi = daftarRekomendasi;
    }
}
