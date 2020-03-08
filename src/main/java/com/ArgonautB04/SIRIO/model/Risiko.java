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
    private int id_risiko;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String nama_risiko;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String risiko_kategori;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false)
    private String indikator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "id_status_risiko", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusRisiko status_risiko;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent", referencedColumnName = "id_risiko", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Risiko parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty
    private List<Risiko> childList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sop", referencedColumnName = "id_sop", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SOP sop;

    public int getId_risiko() {
        return id_risiko;
    }

    public void setId_risiko(int id_risiko) {
        this.id_risiko = id_risiko;
    }

    public String getNama_risiko() {
        return nama_risiko;
    }

    public void setNama_risiko(String nama_risiko) {
        this.nama_risiko = nama_risiko;
    }

    public String getRisiko_kategori() {
        return risiko_kategori;
    }

    public void setRisiko_kategori(String risiko_kategori) {
        this.risiko_kategori = risiko_kategori;
    }

    public String getIndikator() {
        return indikator;
    }

    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    public StatusRisiko getStatus_risiko() {
        return status_risiko;
    }

    public void setStatus_risiko(StatusRisiko status_risiko) {
        this.status_risiko = status_risiko;
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
