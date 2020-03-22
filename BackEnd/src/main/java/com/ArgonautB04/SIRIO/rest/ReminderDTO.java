package com.ArgonautB04.SIRIO.rest;

public class ReminderDTO {
    private int id;
    private int idPembuat;
    private String tanggal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(int idPembuat) {
        this.idPembuat = idPembuat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
