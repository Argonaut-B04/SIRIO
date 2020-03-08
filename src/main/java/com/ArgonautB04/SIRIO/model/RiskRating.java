package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Risk_Rating")
public class RiskRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rating;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false)
    private String nama_rating;

    @NotNull
    @Column(nullable = false, scale = 3, precision = 6, unique = true)
    private float skor_minimal;

    @NotNull
    @Column(nullable = false, scale = 3, precision = 6, unique = true)
    private float skor_maksimal;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan_rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pengelola", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pengelola;

    public int getId_rating() {
        return id_rating;
    }

    public void setId_rating(int id_rating) {
        this.id_rating = id_rating;
    }

    public String getNama_rating() {
        return nama_rating;
    }

    public void setNama_rating(String nama_rating) {
        this.nama_rating = nama_rating;
    }

    public float getSkor_minimal() {
        return skor_minimal;
    }

    public void setSkor_minimal(float skor_minimal) {
        this.skor_minimal = skor_minimal;
    }

    public float getSkor_maksimal() {
        return skor_maksimal;
    }

    public void setSkor_maksimal(float skor_maksimal) {
        this.skor_maksimal = skor_maksimal;
    }

    public String getKeterangan_rating() {
        return keterangan_rating;
    }

    public void setKeterangan_rating(String keterangan_rating) {
        this.keterangan_rating = keterangan_rating;
    }

    public Employee getPengelola() {
        return pengelola;
    }

    public void setPengelola(Employee pengelola) {
        this.pengelola = pengelola;
    }
}
