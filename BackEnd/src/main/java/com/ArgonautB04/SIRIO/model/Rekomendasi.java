package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
public class Rekomendasi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRekomendasi;

//    @NotNull
//    @Size(max = 75)
//    @Column(nullable = false)
//    private String nama;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate tenggatWaktu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusRekomendasi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRekomendasi statusRekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "komponen_pemeriksaan", referencedColumnName = "idKomponenPemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomponenPemeriksaan komponenPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public Integer getIdRekomendasi() {
        return idRekomendasi;
    }

    public void setIdRekomendasi(Integer idRekomendasi) {
        this.idRekomendasi = idRekomendasi;
    }

//    public String getNama() {
//        return nama;
//    }
//
//    public void setNama(String nama) {
//        this.nama = nama;
//    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public LocalDate getTenggatWaktu() {
        return tenggatWaktu;
    }

    public void setTenggatWaktu(LocalDate tenggatWaktu) {
        this.tenggatWaktu = tenggatWaktu;
    }

    public StatusRekomendasi getStatusRekomendasi() {
        return statusRekomendasi;
    }

    public void setStatusRekomendasi(StatusRekomendasi statusRekomendasi) {
        this.statusRekomendasi = statusRekomendasi;
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
