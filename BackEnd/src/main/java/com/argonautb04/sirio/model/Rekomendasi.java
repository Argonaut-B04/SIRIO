package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
public class Rekomendasi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRekomendasi;

    @NotNull
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String keterangan;

    @DateTimeFormat
    private LocalDate tenggatWaktu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusRekomendasi", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private StatusRekomendasi statusRekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "komponen_pemeriksaan", referencedColumnName = "idKomponenPemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomponenPemeriksaan komponenPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    @ManyToOne
    @JoinColumn(name = "reminderTemplate", referencedColumnName = "idReminderTemplate")
    @JsonIgnore
    private ReminderTemplate reminderTemplatePilihan;

    @OneToOne(mappedBy = "rekomendasi", cascade = CascadeType.ALL)
    private BuktiPelaksanaan buktiPelaksanaan;

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

    public BuktiPelaksanaan getBuktiPelaksanaan() {
        return buktiPelaksanaan;
    }

    public void setBuktiPelaksanaan(BuktiPelaksanaan buktiPelaksanaan) {
        this.buktiPelaksanaan = buktiPelaksanaan;
    }

    public ReminderTemplate getReminderTemplatePilihan() {
        return reminderTemplatePilihan;
    }

    public void setReminderTemplatePilihan(ReminderTemplate reminderTemplatePilihan) {
        this.reminderTemplatePilihan = reminderTemplatePilihan;
    }
}
