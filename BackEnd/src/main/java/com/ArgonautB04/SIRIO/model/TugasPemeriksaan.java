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
@Table
public class TugasPemeriksaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTugas;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyy")
    @Column(name = "tanggal_mulai", nullable = false)
    private LocalDate tanggalMulai;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyy")
    @Column(name = "tanggal_selesai", nullable = false)
    private LocalDate tanggalSelesai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kantor_cabang", referencedColumnName = "idKantor", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KantorCabang kantorCabang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rencana_pemeriksaan", referencedColumnName = "idRencana", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RencanaPemeriksaan rencanaPemeriksaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pelaksana", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Employee pelaksana;

    public int getIdTugas() {
        return idTugas;
    }

    public void setIdTugas(int idTugas) {
        this.idTugas = idTugas;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public LocalDate getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(LocalDate tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public KantorCabang getKantorCabang() {
        return kantorCabang;
    }

    public void setKantorCabang(KantorCabang kantorCabang) {
        this.kantorCabang = kantorCabang;
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
