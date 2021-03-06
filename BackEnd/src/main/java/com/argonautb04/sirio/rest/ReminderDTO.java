package com.argonautb04.sirio.rest;

import java.time.LocalDate;

public class ReminderDTO {
    private int idReminder;
    private int idPembuat;
    private LocalDate tanggalPengiriman;

    public LocalDate getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(final LocalDate tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(final int idReminder) {
        this.idReminder = idReminder;
    }

    public int getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(final int idPembuat) {
        this.idPembuat = idPembuat;
    }
}
