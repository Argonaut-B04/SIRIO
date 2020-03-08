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
    private int id_rekomendasi;

    @NotNull
    @Size(max = 75)
    @Column(nullable = false)
    private String nama;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate tenggat_waktu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "id_status_rekomendasi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRekomendasi status_rekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "komponen_pemeriksaan", referencedColumnName = "id_komponen_pemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomponenPemeriksaan komponen_pemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getId_rekomendasi() {
        return id_rekomendasi;
    }

    public void setId_rekomendasi(int id_rekomendasi) {
        this.id_rekomendasi = id_rekomendasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public LocalDate getTenggat_waktu() {
        return tenggat_waktu;
    }

    public void setTenggat_waktu(LocalDate tenggat_waktu) {
        this.tenggat_waktu = tenggat_waktu;
    }

    public StatusRekomendasi getStatus_rekomendasi() {
        return status_rekomendasi;
    }

    public void setStatus_rekomendasi(StatusRekomendasi status_rekomendasi) {
        this.status_rekomendasi = status_rekomendasi;
    }

    public KomponenPemeriksaan getKomponen_pemeriksaan() {
        return komponen_pemeriksaan;
    }

    public void setKomponen_pemeriksaan(KomponenPemeriksaan komponen_pemeriksaan) {
        this.komponen_pemeriksaan = komponen_pemeriksaan;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
