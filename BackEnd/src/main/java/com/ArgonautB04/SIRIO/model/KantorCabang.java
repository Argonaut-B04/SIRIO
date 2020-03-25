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
public class KantorCabang implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idKantor;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false, unique = true)
    private String namaKantor;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false, unique = true)
    private String regional;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false, unique = true)
    private String area;

    @NotNull
    @Column(nullable = false)
    private boolean kunjunganAudit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risk_rating", referencedColumnName = "idRating", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RiskRating riskRating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemilik", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pemilik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getIdKantor() {
        return idKantor;
    }

    public void setIdKantor(int idKantor) {
        this.idKantor = idKantor;
    }

    public String getNamaKantor() {
        return namaKantor;
    }

    public void setNamaKantor(String namaKantor) {
        this.namaKantor = namaKantor;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean getKunjunganAudit() {
        return kunjunganAudit;
    }

    public void setKunjunganAudit(boolean kunjunganAuditRisk) {
        this.kunjunganAudit = kunjunganAuditRisk;
    }

    public RiskRating getRiskRating() {
        return riskRating;
    }

    public void setRiskRating(RiskRating riskRating) {
        this.riskRating = riskRating;
    }

    public Employee getPemilik() {
        return pemilik;
    }

    public void setPemilik(Employee pemilik) {
        this.pemilik = pemilik;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
