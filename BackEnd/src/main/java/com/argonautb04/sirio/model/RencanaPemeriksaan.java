package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class RencanaPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRencana;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String namaRencana;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String linkMajelis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusRencana", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRencanaPemeriksaan status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    public int getIdRencana() {
        return idRencana;
    }

    public void setIdRencana(int idRencana) {
        this.idRencana = idRencana;
    }

    public String getNamaRencana() {
        return namaRencana;
    }

    public void setNamaRencana(String namaRencana) {
        this.namaRencana = namaRencana;
    }

    public String getLinkMajelis() {
        return linkMajelis;
    }

    public void setLinkMajelis(String linkMajelis) {
        this.linkMajelis = linkMajelis;
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
