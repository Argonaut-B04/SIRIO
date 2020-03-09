package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Kantor_Cabang")
public class KantorCabang implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_kantor;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false, unique = true)
    private String nama_kantor;

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
    private int jumlah_kunjungan_audit_risk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risk_rating", referencedColumnName = "id_rating", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RiskRating risk_rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemilik", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pemilik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getId_kantor() {
        return id_kantor;
    }

    public void setId_kantor(int id_kantor) {
        this.id_kantor = id_kantor;
    }

    public String getNama_kantor() {
        return nama_kantor;
    }

    public void setNama_kantor(String nama_kantor) {
        this.nama_kantor = nama_kantor;
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

    public int getJumlah_kunjungan_audit_risk() {
        return jumlah_kunjungan_audit_risk;
    }

    public void setJumlah_kunjungan_audit_risk(int jumlah_kunjungan_audit_risk) {
        this.jumlah_kunjungan_audit_risk = jumlah_kunjungan_audit_risk;
    }

    public RiskRating getRisk_rating() {
        return risk_rating;
    }

    public void setRisk_rating(RiskRating risk_rating) {
        this.risk_rating = risk_rating;
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
