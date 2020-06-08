package com.argonautb04.sirio.rest;

import java.time.LocalDate;
import java.util.List;

public class RekomendasiDTO {
    private Integer id;
    private Integer idPembuat;
    private Integer idHasilPemeriksaan;
    private String keterangan;
    private String tenggatWaktu;
    private LocalDate tenggatWaktuDate;
    private String durasi;
    private String status;
    private String statusBukti;
    private String namaKantorCabang;
    private List<ReminderDTO> reminderDTO;

    public List<ReminderDTO> getReminder() {
        return reminderDTO;
    }

    public void setReminder(final List<ReminderDTO> reminderDTO) {
        this.reminderDTO = reminderDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(final Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public Integer getIdHasilPemeriksaan() {
        return idHasilPemeriksaan;
    }

    public void setIdHasilPemeriksaan(final Integer idHasilPemeriksaan) {
        this.idHasilPemeriksaan = idHasilPemeriksaan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(final String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTenggatWaktu() {
        return tenggatWaktu;
    }

    public void setTenggatWaktu(final String tenggatWaktu) {
        this.tenggatWaktu = tenggatWaktu;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(final String durasi) {
        this.durasi = durasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatusBukti() {
        return statusBukti;
    }

    public void setStatusBukti(final String statusBukti) {
        this.statusBukti = statusBukti;
    }

    public String getNamaKantorCabang() {
        return namaKantorCabang;
    }

    public void setNamaKantorCabang(final String namaKantorCabang) {
        this.namaKantorCabang = namaKantorCabang;
    }

    public LocalDate getTenggatWaktuDate() {
        return tenggatWaktuDate;
    }

    public void setTenggatWaktuDate(final LocalDate tenggatWaktuDate) {
        this.tenggatWaktuDate = tenggatWaktuDate;
    }

}
