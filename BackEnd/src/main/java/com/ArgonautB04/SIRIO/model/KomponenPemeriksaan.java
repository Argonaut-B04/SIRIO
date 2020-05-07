package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class KomponenPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKomponenPemeriksaan;

    @Column
    private Integer jumlahSampel;

    @Column
    private Integer jumlahPopulasi;

    @Column
    private Integer jumlahSampelError;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String keteranganSampel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risiko", referencedColumnName = "idRisiko", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Risiko risiko;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hasil_pemeriksaan", referencedColumnName = "idHasilPemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HasilPemeriksaan hasilPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risk_level", referencedColumnName = "idLevel")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RiskLevel riskLevel;

    public Integer getIdKomponenPemeriksaan() {
        return idKomponenPemeriksaan;
    }

    public void setIdKomponenPemeriksaan(Integer idKomponenPemeriksaan) {
        this.idKomponenPemeriksaan = idKomponenPemeriksaan;
    }

    public Integer getJumlahPopulasi() {
        return jumlahPopulasi;
    }

    public void setJumlahPopulasi(Integer jumlahPopulasi) {
        this.jumlahPopulasi = jumlahPopulasi;
    }

    public Integer getJumlahSampelError() {
        return jumlahSampelError;
    }

    public void setJumlahSampelError(Integer jumlahSampelError) {
        this.jumlahSampelError = jumlahSampelError;
    }

    public Integer getJumlahSampel() {
        return jumlahSampel;
    }

    public void setJumlahSampel(Integer jumlahSampel) {
        this.jumlahSampel = jumlahSampel;
    }

    public String getKeteranganSampel() {
        return keteranganSampel;
    }

    public void setKeteranganSampel(String keteranganSampel) {
        this.keteranganSampel = keteranganSampel;
    }

    public Risiko getRisiko() {
        return risiko;
    }

    public void setRisiko(Risiko risiko) {
        this.risiko = risiko;
    }

    public HasilPemeriksaan getHasilPemeriksaan() {
        return hasilPemeriksaan;
    }

    public void setHasilPemeriksaan(HasilPemeriksaan hasilPemeriksaan) {
        this.hasilPemeriksaan = hasilPemeriksaan;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
}
