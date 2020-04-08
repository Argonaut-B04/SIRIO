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
public class RiskRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRating;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false)
    private String namaRating;

    @NotNull
    @Column(nullable = false, scale = 3, precision = 6, unique = true)
    private float skorMinimal;

    @NotNull
    @Column(nullable = false, scale = 3, precision = 6, unique = true)
    private float skorMaksimal;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keteranganRating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pengelola", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pengelola;

    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public String getNamaRating() {
        return namaRating;
    }

    public void setNamaRating(String namaRating) {
        this.namaRating = namaRating;
    }

    public float getSkorMinimal() {
        return skorMinimal;
    }

    public void setSkorMinimal(float skorMinimal) {
        this.skorMinimal = skorMinimal;
    }

    public float getSkorMaksimal() {
        return skorMaksimal;
    }

    public void setSkorMaksimal(float skorMaksimal) {
        this.skorMaksimal = skorMaksimal;
    }

    public String getKeteranganRating() {
        return keteranganRating;
    }

    public void setKeteranganRating(String keteranganRating) {
        this.keteranganRating = keteranganRating;
    }

    public Employee getPengelola() {
        return pengelola;
    }

    public void setPengelola(Employee pengelola) {
        this.pengelola = pengelola;
    }
}
