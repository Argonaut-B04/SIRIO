package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class HasilPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHasilPemeriksaan;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaHasilPemeriksaan;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String feedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusHasil", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusHasilPemeriksaan statusHasilPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tugas_pemeriksaan", referencedColumnName = "idTugas", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TugasPemeriksaan tugasPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemeriksa", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pemeriksa;

    public int getIdHasilPemeriksaan() {
        return idHasilPemeriksaan;
    }

    public void setIdHasilPemeriksaan(int idHasilPemeriksaan) {
        this.idHasilPemeriksaan = idHasilPemeriksaan;
    }

    public String getNamaHasilPemeriksaan() {
        return namaHasilPemeriksaan;
    }

    public void setNamaHasilPemeriksaan(String namaHasilPemeriksaan) {
        this.namaHasilPemeriksaan = namaHasilPemeriksaan;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public StatusHasilPemeriksaan getStatusHasilPemeriksaan() {
        return statusHasilPemeriksaan;
    }

    public void setStatusHasilPemeriksaan(StatusHasilPemeriksaan statusHasilPemeriksaan) {
        this.statusHasilPemeriksaan = statusHasilPemeriksaan;
    }

    public TugasPemeriksaan getTugasPemeriksaan() {
        return tugasPemeriksaan;
    }

    public void setTugasPemeriksaan(TugasPemeriksaan tugasPemeriksaan) {
        this.tugasPemeriksaan = tugasPemeriksaan;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }

    public Employee getPemeriksa() {
        return pemeriksa;
    }

    public void setPemeriksa(Employee pemeriksa) {
        this.pemeriksa = pemeriksa;
    }
}
