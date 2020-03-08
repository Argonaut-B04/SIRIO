package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Tugas_Pemeriksaan")
public class TugasPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tugas;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyy")
    @Column(name = "tanggal_mulai", nullable = false)
    private LocalDate tanggal_mulai;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyy")
    @Column(name = "tanggal_selesai", nullable = false)
    private LocalDate tanggal_selesai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kantor_cabang", referencedColumnName = "id_kantor", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KantorCabang kantor_cabang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rencana_pemeriksaan", referencedColumnName = "id_rencana", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RencanaPemeriksaan rencanaPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pelaksana", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pelaksana;

    public int getId_tugas() {
        return id_tugas;
    }

    public void setId_tugas(int id_tugas) {
        this.id_tugas = id_tugas;
    }

    public LocalDate getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(LocalDate tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public LocalDate getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(LocalDate tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public KantorCabang getKantor_cabang() {
        return kantor_cabang;
    }

    public void setKantor_cabang(KantorCabang kantor_cabang) {
        this.kantor_cabang = kantor_cabang;
    }

    public RencanaPemeriksaan getRencanaPemeriksaan() {
        return rencanaPemeriksaan;
    }

    public void setRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan) {
        this.rencanaPemeriksaan = rencanaPemeriksaan;
    }

    public Employee getPelaksana() {
        return pelaksana;
    }

    public void setPelaksana(Employee pelaksana) {
        this.pelaksana = pelaksana;
    }
}
