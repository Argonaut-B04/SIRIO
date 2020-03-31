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
public class RiskLevel implements Serializable {

    enum Status {
        AKTIF, NONAKTIF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLevel;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaLevel;

    @NotNull
    @Column(nullable = false, scale = 3, precision = 6, unique = true)
    private float bobotLevel;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keteranganLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pengelola", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pengelola;

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getNamaLevel() {
        return namaLevel;
    }

    public void setNamaLevel(String namaLevel) {
        this.namaLevel = namaLevel;
    }

    public float getBobotLevel() {
        return bobotLevel;
    }

    public void setBobotLevel(float bobotLevel) {
        this.bobotLevel = bobotLevel;
    }

    public String getKeteranganLevel() {
        return keteranganLevel;
    }

    public void setKeteranganLevel(String keteranganLevel) {
        this.keteranganLevel = keteranganLevel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public Employee getPengelola() {
        return pengelola;
    }

    public void setPengelola(Employee pengelola) {
        this.pengelola = pengelola;
    }
}
