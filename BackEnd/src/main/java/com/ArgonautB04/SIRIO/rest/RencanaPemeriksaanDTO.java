package com.argonautb04.sirio.rest;

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

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNamaRencana() {
        return namaRencana;
    }

    public void setNamaRencana(final String namaRencana) {
        this.namaRencana = namaRencana;
    }

    public String getLinkMajelis() {
        return linkMajelis;
    }

    public void setLinkMajelis(final String linkMajelis) {
        this.linkMajelis = linkMajelis;
    }

    public Integer getIdPembuat() {
        return idPembuat;
    }

    public void setIdPembuat(final Integer idPembuat) {
        this.idPembuat = idPembuat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public List<TugasPemeriksaanDTO> getDaftarTugasPemeriksaan() {
        return daftarTugasPemeriksaan;
    }

    public void setDaftarTugasPemeriksaan(final List<TugasPemeriksaanDTO> daftarTugasPemeriksaan) {
        this.daftarTugasPemeriksaan = daftarTugasPemeriksaan;
    }

}
