package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import org.hibernate.annotations.Type;

@Entity
@Table
public class TemuanRisiko implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTemuanRisiko;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String keterangan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "komponen_pemeriksaan", referencedColumnName = "idKomponenPemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomponenPemeriksaan komponenPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    public Integer getIdTemuanRisiko() {
        return idTemuanRisiko;
    }

    public void setIdTemuanRisiko(Integer idTemuanRisiko) {
        this.idTemuanRisiko = idTemuanRisiko;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public KomponenPemeriksaan getKomponenPemeriksaan() {
        return komponenPemeriksaan;
    }

    public void setKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        this.komponenPemeriksaan = komponenPemeriksaan;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
