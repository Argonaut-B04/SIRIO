package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class StatusBuktiPelaksanaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStatusBukti;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaStatus;

    @Size(max = 125)
    @Column
    private String keteranganStatus;

    public StatusBuktiPelaksanaan() {
    }

    public StatusBuktiPelaksanaan(@NotNull @Size(max = 50) String namaStatus) {
        this.namaStatus = namaStatus;
    }

    public int getIdStatusBukti() {
        return idStatusBukti;
    }

    public void setIdStatusBukti(int idStatusBukti) {
        this.idStatusBukti = idStatusBukti;
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
