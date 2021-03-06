package com.argonautb04.sirio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Size(max = 125)
    @Column
    private String keteranganStatus;

    @NotNull
    @Column
    private boolean dapatSetTenggatWaktu;

    public StatusRekomendasi() {
    }

    public StatusRekomendasi(@NotNull @Size(max = 50) String namaStatus, @NotNull boolean dapatSetTenggatWaktu) {
        this.namaStatus = namaStatus;
        this.dapatSetTenggatWaktu = dapatSetTenggatWaktu;
    }

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

    public boolean isDapatSetTenggatWaktu() {
        return dapatSetTenggatWaktu;
    }

    public void setDapatSetTenggatWaktu(boolean dapatSetTenggatWaktu) {
        this.dapatSetTenggatWaktu = dapatSetTenggatWaktu;
    }
}
