package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Risk_Level")
public class RiskLevel implements Serializable {

    enum Status {
        AKTIF, NONAKTIF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_level;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String nama_level;

    @NotNull
    @Column(nullable = false, scale = 3, precision = 6, unique = true)
    private float bobot_level;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan_level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Employee.Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pengelola", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pengelola;

    public int getId_level() {
        return id_level;
    }

    public void setId_level(int id_level) {
        this.id_level = id_level;
    }

    public String getNama_level() {
        return nama_level;
    }

    public void setNama_level(String nama_level) {
        this.nama_level = nama_level;
    }

    public float getBobot_level() {
        return bobot_level;
    }

    public void setBobot_level(float bobot_level) {
        this.bobot_level = bobot_level;
    }

    public String getKeterangan_level() {
        return keterangan_level;
    }

    public void setKeterangan_level(String keterangan_level) {
        this.keterangan_level = keterangan_level;
    }

    public Employee.Status getStatus() {
        return status;
    }

    public void setStatus(Employee.Status status) {
        this.status = status;
    }

    public Employee getPengelola() {
        return pengelola;
    }

    public void setPengelola(Employee pengelola) {
        this.pengelola = pengelola;
    }
}
