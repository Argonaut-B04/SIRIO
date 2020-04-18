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
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Rekomendasi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRekomendasi;

    @NotNull
    @Size(max = 125)
    @Column(nullable = true)
    private String keterangan;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date tenggatWaktu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusRekomendasi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRekomendasi statusRekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "komponen_pemeriksaan", referencedColumnName = "idKomponenPemeriksaan", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomponenPemeriksaan komponenPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    @OneToOne(mappedBy = "rekomendasi", cascade = CascadeType.ALL)
    private BuktiPelaksanaan buktiPelaksanaan;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Reminder> daftarReminder;

    public Integer getIdRekomendasi() {
        return idRekomendasi;
    }

    public void setIdRekomendasi(Integer idRekomendasi) {
        this.idRekomendasi = idRekomendasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getTenggatWaktu() {
        return tenggatWaktu;
    }

    public void setTenggatWaktu(Date tenggatWaktu) {
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

    public BuktiPelaksanaan getBuktiPelaksanaan() {
        return buktiPelaksanaan;
    }

    public void setBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan) {
        this.buktiPelaksanaan = buktiPelaksanaan;
    }

    public List<Reminder> getDaftarReminder() {
        return daftarReminder;
    }

    public void setDaftarReminder(List<Reminder> daftarReminder) {
        this.daftarReminder.retainAll(daftarReminder);
        this.daftarReminder.addAll(daftarReminder);
    }
}
