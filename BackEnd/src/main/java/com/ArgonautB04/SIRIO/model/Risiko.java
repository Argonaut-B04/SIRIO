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
    private String risikoKategori;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false)
    private String indikator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusRisiko", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRisiko statusRisiko;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent", referencedColumnName = "idRisiko", nullable = false)
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

    public String getRisikoKategori() {
        return risikoKategori;
    }

    public void setRisikoKategori(String risikoKategori) {
        this.risikoKategori = risikoKategori;
    }

    public String getIndikator() {
        return indikator;
    }

    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    public StatusRisiko getStatusRisiko() {
        return statusRisiko;
    }

    public void setStatusRisiko(StatusRisiko statusRisiko) {
        this.statusRisiko = statusRisiko;
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
