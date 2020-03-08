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
}
