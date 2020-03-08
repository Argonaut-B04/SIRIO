package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Komponen_Pemeriksaan")
public class KomponenPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_komponen_pemeriksaan;

    @NotNull
    @Column
    private int jumlah_sampel;

    @NotNull
    @Size(max = 125)
    @Column
    private String keterangan_sampel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risiko", referencedColumnName = "id_risiko", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Risiko risiko;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hasil_pemeriksaan", referencedColumnName = "id_hasil_pemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HasilPemeriksaan hasil_pemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risk_level", referencedColumnName = "id_level", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RiskLevel risk_level;

    public int getId_komponen_pemeriksaan() {
        return id_komponen_pemeriksaan;
    }

    public void setId_komponen_pemeriksaan(int id_komponen_pemeriksaan) {
        this.id_komponen_pemeriksaan = id_komponen_pemeriksaan;
    }

    public int getJumlah_sampel() {
        return jumlah_sampel;
    }

    public void setJumlah_sampel(int jumlah_sampel) {
        this.jumlah_sampel = jumlah_sampel;
    }

    public String getKeterangan_sampel() {
        return keterangan_sampel;
    }

    public void setKeterangan_sampel(String keterangan_sampel) {
        this.keterangan_sampel = keterangan_sampel;
    }

    public Risiko getRisiko() {
        return risiko;
    }

    public void setRisiko(Risiko risiko) {
        this.risiko = risiko;
    }

    public HasilPemeriksaan getHasil_pemeriksaan() {
        return hasil_pemeriksaan;
    }

    public void setHasil_pemeriksaan(HasilPemeriksaan hasil_pemeriksaan) {
        this.hasil_pemeriksaan = hasil_pemeriksaan;
    }

    public RiskLevel getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(RiskLevel risk_level) {
        this.risk_level = risk_level;
    }
}
