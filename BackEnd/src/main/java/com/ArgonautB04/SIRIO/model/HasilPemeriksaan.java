package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Hasil_Pemeriksaan")
public class HasilPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_hasil_pemeriksaan;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String nama_hasil_pemeriksaan;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String feedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "id_status_hasil", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusHasilPemeriksaan status_hasil_pemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tugas_pemeriksaan", referencedColumnName = "id_tugas", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TugasPemeriksaan tugas_pemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemeriksa", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pemeriksa;

    public int getId_hasil_pemeriksaan() {
        return id_hasil_pemeriksaan;
    }

    public void setId_hasil_pemeriksaan(int id_hasil_pemeriksaan) {
        this.id_hasil_pemeriksaan = id_hasil_pemeriksaan;
    }

    public String getNama_hasil_pemeriksaan() {
        return nama_hasil_pemeriksaan;
    }

    public void setNama_hasil_pemeriksaan(String nama_hasil_pemeriksaan) {
        this.nama_hasil_pemeriksaan = nama_hasil_pemeriksaan;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public StatusHasilPemeriksaan getStatus_hasil_pemeriksaan() {
        return status_hasil_pemeriksaan;
    }

    public void setStatus_hasil_pemeriksaan(StatusHasilPemeriksaan status_hasil_pemeriksaan) {
        this.status_hasil_pemeriksaan = status_hasil_pemeriksaan;
    }

    public TugasPemeriksaan getTugas_pemeriksaan() {
        return tugas_pemeriksaan;
    }

    public void setTugas_pemeriksaan(TugasPemeriksaan tugas_pemeriksaan) {
        this.tugas_pemeriksaan = tugas_pemeriksaan;
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
