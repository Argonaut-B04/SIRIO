package com.ArgonautB04.SIRIO.rest;

import java.util.List;

public class RencanaPemeriksaanDTO {
    private Integer id;
    private String namaRencana;
    private String linkMajelis;
    private Integer idPembuat;
    private Integer status;
    private List<TugasPemeriksaanDTO> daftarTugasPemeriksaan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaRencana() {
        return namaRencana;
    }

    public void setNamaRencana(String namaRencana) {
        this.namaRencana = namaRencana;
    }

    public String getLinkMajelis() {
        return linkMajelis;
    }

    public void setLinkMajelis(String linkMajelis) {
        this.linkMajelis = linkMajelis;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TugasPemeriksaanDTO> getDaftarTugasPemeriksaan() {
        return daftarTugasPemeriksaan;
    }

    public void setDaftarTugasPemeriksaan(List<TugasPemeriksaanDTO> daftarTugasPemeriksaan) {
        this.daftarTugasPemeriksaan = daftarTugasPemeriksaan;
    }


}
