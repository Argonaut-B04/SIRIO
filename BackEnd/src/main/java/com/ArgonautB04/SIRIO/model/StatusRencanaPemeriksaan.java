package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class StatusRencanaPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStatusRencana;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaStatus;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keteranganStatus;

    public int getIdStatusRencana() {
        return idStatusRencana;
    }

    public void setIdStatusRencana(int idStatusRencana) {
        this.idStatusRencana = idStatusRencana;
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
