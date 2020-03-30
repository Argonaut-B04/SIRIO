package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Risiko implements Serializable {

    public static enum Status {
        AKTIF, NONAKTIF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRisiko;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaRisiko;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private Integer risikoKategori;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false)
    private String komponen;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent", referencedColumnName = "idRisiko", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Risiko parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty
    private List<Risiko> childList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sop", referencedColumnName = "idSop", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SOP sop;

    public int getIdRisiko() {
        return idRisiko;
    }

    public void setIdRisiko(int idRisiko) {
        this.idRisiko = idRisiko;
    }

    public String getNamaRisiko() {
        return namaRisiko;
    }

    public void setNamaRisiko(String namaRisiko) {
        this.namaRisiko = namaRisiko;
    }

    public Integer getRisikoKategori() {
        return risikoKategori;
    }

    public void setRisikoKategori(Integer risikoKategori) {
        this.risikoKategori = risikoKategori;
    }

    public String getKomponen() {
        return komponen;
    }

    public void setKomponen(String indikator) {
        this.komponen = indikator;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Risiko getParent() {
        return parent;
    }

    public void setParent(Risiko parent) {
        this.parent = parent;
    }

    public List<Risiko> getChildList() {
        return childList;
    }

    public void setChildList(List<Risiko> childList) {
        this.childList = childList;
    }

    public SOP getSop() {
        return sop;
    }

    public void setSop(SOP sop) {
        this.sop = sop;
    }
}
