package com.ArgonautB04.SIRIO.rest;

import java.time.LocalDate;

public class ReminderDTO {
    private int idReminder;
    private int idPembuat;
    private LocalDate tanggalPengiriman;

    public LocalDate getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(LocalDate tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(int idReminder) {
        this.idReminder = idReminder;
    }

    public int getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(int idPembuat) {
        this.idPembuat = idPembuat;
    }
}
