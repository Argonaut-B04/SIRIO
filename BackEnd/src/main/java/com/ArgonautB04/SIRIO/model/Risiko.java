package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @Column(nullable = false)
    private Integer risikoKategori;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    @Size(max = 500)
    private String detailUraian;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    @Size(max = 500)
    private String deskripsi;

    @Size(max = 50)
    @Column
    private String metodologi;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    @Size(max = 500)
    private String ketentuanSampel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent", referencedColumnName = "idRisiko")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Risiko parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty
    private List<Risiko> childList;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sop", referencedColumnName = "idSop")
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public String getDetailUraian() {
        return detailUraian;
    }

    public void setDetailUraian(String detailUraian) {
        this.detailUraian = detailUraian;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getMetodologi() {
        return metodologi;
    }

    public void setMetodologi(String metodologi) {
        this.metodologi = metodologi;
    }

    public String getKetentuanSampel() {
        return ketentuanSampel;
    }

    public void setKetentuanSampel(String ketentuanSampel) {
        this.ketentuanSampel = ketentuanSampel;
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

    public enum Status {
        AKTIF, NONAKTIF
    }
}
