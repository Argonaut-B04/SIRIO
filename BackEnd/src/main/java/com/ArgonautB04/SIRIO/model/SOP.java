package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class SOP implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_sop;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String judul;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false)
    private String kategori;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String link_dokumen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getId_sop() {
        return id_sop;
    }

    public void setId_sop(int id_sop) {
        this.id_sop = id_sop;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getLink_dokumen() {
        return link_dokumen;
    }

    public void setLink_dokumen(String link_dokumen) {
        this.link_dokumen = link_dokumen;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
