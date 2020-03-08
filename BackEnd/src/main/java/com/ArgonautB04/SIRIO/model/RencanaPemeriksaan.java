package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Rencana_Pemeriksaan")
public class RencanaPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rencana;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String nama_rencana;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String link_majelis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "id_status_rencana", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRencanaPemeriksaan status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getId_rencana() {
        return id_rencana;
    }

    public void setId_rencana(int id_rencana) {
        this.id_rencana = id_rencana;
    }

    public String getNama_rencana() {
        return nama_rencana;
    }

    public void setNama_rencana(String nama_rencana) {
        this.nama_rencana = nama_rencana;
    }

    public String getLink_majelis() {
        return link_majelis;
    }

    public void setLink_majelis(String link_majelis) {
        this.link_majelis = link_majelis;
    }

    public StatusRencanaPemeriksaan getStatus() {
        return status;
    }

    public void setStatus(StatusRencanaPemeriksaan status) {
        this.status = status;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
