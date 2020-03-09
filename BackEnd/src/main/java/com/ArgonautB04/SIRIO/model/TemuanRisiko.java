package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Temuan_Risiko")
public class TemuanRisiko implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_temuan_risiko;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "komponen_pemeriksaan", referencedColumnName = "id_komponen_pemeriksaan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomponenPemeriksaan komponen_pemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getId_temuan_risiko() {
        return id_temuan_risiko;
    }

    public void setId_temuan_risiko(int id_temuan_risiko) {
        this.id_temuan_risiko = id_temuan_risiko;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public KomponenPemeriksaan getKomponen_pemeriksaan() {
        return komponen_pemeriksaan;
    }

    public void setKomponen_pemeriksaan(KomponenPemeriksaan komponen_pemeriksaan) {
        this.komponen_pemeriksaan = komponen_pemeriksaan;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
