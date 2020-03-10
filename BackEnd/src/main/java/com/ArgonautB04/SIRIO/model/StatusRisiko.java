package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class StatusRisiko implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStatusRisiko;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaStatus;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keteranganStatus;

    public int getIdStatusRisiko() {
        return idStatusRisiko;
    }

    public void setIdStatusRisiko(int idStatusRisiko) {
        this.idStatusRisiko = idStatusRisiko;
    }

    public String getNamaStatus() {
        return namaStatus;
    }

    public void setNamaStatus(String namaStatus) {
        this.namaStatus = namaStatus;
    }

    public String getKeteranganStatus() {
        return keteranganStatus;
    }

    public void setKeteranganStatus(String keteranganStatus) {
        this.keteranganStatus = keteranganStatus;
    }
}
