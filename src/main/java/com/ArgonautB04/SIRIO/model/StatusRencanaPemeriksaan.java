package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Status_Rencana_Pemeriksaan")
public class StatusRencanaPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_status_rencana;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String nama_status;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan_status;

    public int getId_status_rencana() {
        return id_status_rencana;
    }

    public void setId_status_rencana(int id_status_rencana) {
        this.id_status_rencana = id_status_rencana;
    }

    public String getNama_status() {
        return nama_status;
    }

    public void setNama_status(String nama_status) {
        this.nama_status = nama_status;
    }

    public String getKeterangan_status() {
        return keterangan_status;
    }

    public void setKeterangan_status(String keterangan_status) {
        this.keterangan_status = keterangan_status;
    }
}
