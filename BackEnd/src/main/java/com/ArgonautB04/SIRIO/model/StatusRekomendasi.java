package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class StatusRekomendasi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStatusRekomendasi;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaStatus;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keteranganStatus;

    public int getIdStatusRekomendasi() {
        return idStatusRekomendasi;
    }

    public void setIdStatusRekomendasi(int idStatusRekomendasi) {
        this.idStatusRekomendasi = idStatusRekomendasi;
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
