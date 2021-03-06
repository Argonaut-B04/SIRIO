package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class RiskLevel implements Serializable {

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

    public void setStatus(Status status) {
        this.status = status;
    }

    public Employee getPengelola() {
        return pengelola;
    }

    public void setPengelola(Employee pengelola) {
        this.pengelola = pengelola;
    }

    public enum Status {
        AKTIF, NONAKTIF
    }
}
