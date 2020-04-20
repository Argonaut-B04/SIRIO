package com.ArgonautB04.SIRIO.rest;

import java.time.LocalDate;
import java.util.List;

public class RekomendasiDTO {
    private Integer id;
    private Integer idPembuat;
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

    public void setReminder(List<ReminderDTO> reminderDTO) {
        this.reminderDTO = reminderDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTenggatWaktu() {
        return tenggatWaktu;
    }

    public void setTenggatWaktu(String tenggatWaktu) {
        this.tenggatWaktu = tenggatWaktu;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusBukti() {
        return statusBukti;
    }

    public void setStatusBukti(String statusBukti) {
        this.statusBukti = statusBukti;
    }

    public String getNamaKantorCabang() {
        return namaKantorCabang;
    }

    public void setNamaKantorCabang(String namaKantorCabang) {
        this.namaKantorCabang = namaKantorCabang;
    }

    public LocalDate getTenggatWaktuDate() {
        return tenggatWaktuDate;
    }

    public void setTenggatWaktuDate(LocalDate tenggatWaktuDate) {
        this.tenggatWaktuDate = tenggatWaktuDate;
    }

}
