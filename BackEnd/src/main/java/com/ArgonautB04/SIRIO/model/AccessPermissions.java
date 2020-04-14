package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class AccessPermissions implements Serializable {

    @Id
    @Column(name = "idPermission")
    private Integer idPermission;

    @OneToOne
    @MapsId
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesRiskRating;

    @Column(columnDefinition = "boolean default false")
    private Boolean ubahRiskRating;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesRiskLevel;

    @Column(columnDefinition = "boolean default false")
    private Boolean ubahRiskLevel;

    @Column(columnDefinition = "boolean default false")
    private Boolean ubahReminder;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesTabelRisiko;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesRisiko;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesTambahRisiko;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesUbahRisiko;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesHapusRisiko;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesUbahHierarki;

//    @Column(columnDefinition = "boolean default false")
//    private Boolean aksesKategoriUbahHierarki;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesTabelRekomendasi;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesBuktiPelaksanaan;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesTambahBuktiPelaksanaan;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesUbahBuktiPelaksanaan;

    @Column(columnDefinition = "boolean default false")
    private Boolean aksesPersetujuanBuktiPelaksanaan;

    public AccessPermissions() {
    }

    public AccessPermissions(Role role) {
        this.role = role;
    }

    public Boolean getAksesTabelRisiko() {
        return aksesTabelRisiko;
    }

    public void setAksesTabelRisiko(Boolean aksesTabelRisiko) {
        this.aksesTabelRisiko = aksesTabelRisiko;
    }

    public Boolean getAksesRisiko() {
        return aksesRisiko;
    }

    public void setAksesRisiko(Boolean aksesRisiko) {
        this.aksesRisiko = aksesRisiko;
    }

    public Boolean getAksesTambahRisiko() {
        return aksesTambahRisiko;
    }

    public void setAksesTambahRisiko(Boolean aksesTambahRisiko) {
        this.aksesTambahRisiko = aksesTambahRisiko;
    }

    public Boolean getAksesUbahRisiko() {
        return aksesUbahRisiko;
    }

    public void setAksesUbahRisiko(Boolean aksesUbahRisiko) {
        this.aksesUbahRisiko = aksesUbahRisiko;
    }

    public Boolean getAksesHapusRisiko() {
        return aksesHapusRisiko;
    }

    public void setAksesHapusRisiko(Boolean aksesHapusRisiko) {
        this.aksesHapusRisiko = aksesHapusRisiko;
    }

    public Boolean getAksesUbahHierarki() {
        return aksesUbahHierarki;
    }

    public void setAksesUbahHierarki(Boolean aksesUbahHierarki) {
        this.aksesUbahHierarki = aksesUbahHierarki;
    }

    public Integer getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Integer idPermission) {
        this.idPermission = idPermission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getAksesRiskRating() {
        return aksesRiskRating;
    }

    public void setAksesRiskRating(Boolean aksesRiskRating) {
        this.aksesRiskRating = aksesRiskRating;
    }

    public Boolean getUbahRiskRating() {
        return ubahRiskRating;
    }

    public void setUbahRiskRating(Boolean ubahRiskRating) {
        this.ubahRiskRating = ubahRiskRating;
    }

    public Boolean getAksesRiskLevel() {
        return aksesRiskLevel;
    }

    public void setAksesRiskLevel(Boolean aksesRiskLevel) {
        this.aksesRiskLevel = aksesRiskLevel;
    }

    public Boolean getUbahRiskLevel() {
        return ubahRiskLevel;
    }

    public void setUbahRiskLevel(Boolean ubahRiskLevel) {
        this.ubahRiskLevel = ubahRiskLevel;
    }

    public Boolean getUbahReminder() {
        return ubahReminder;
    }

    public void setUbahReminder(Boolean ubahReminder) {
        this.ubahReminder = ubahReminder;
    }

    public Boolean getAksesTabelRekomendasi() {
        return aksesTabelRekomendasi;
    }

    public void setAksesTabelRekomendasi(Boolean aksesTabelRekomendasi) {
        this.aksesTabelRekomendasi = aksesTabelRekomendasi;
    }

    public Boolean getAksesBuktiPelaksanaan() {
        return aksesBuktiPelaksanaan;
    }

    public void setAksesBuktiPelaksanaan(Boolean aksesBuktiPelaksanaan) {
        this.aksesBuktiPelaksanaan = aksesBuktiPelaksanaan;
    }

    public Boolean getAksesTambahBuktiPelaksanaan() {
        return aksesTambahBuktiPelaksanaan;
    }

    public void setAksesTambahBuktiPelaksanaan(Boolean aksesTambahBuktiPelaksanaan) {
        this.aksesTambahBuktiPelaksanaan = aksesTambahBuktiPelaksanaan;
    }

    public Boolean getAksesUbahBuktiPelaksanaan() {
        return aksesUbahBuktiPelaksanaan;
    }

    public void setAksesUbahBuktiPelaksanaan(Boolean aksesUbahBuktiPelaksanaan) {
        this.aksesUbahBuktiPelaksanaan = aksesUbahBuktiPelaksanaan;
    }

    public Boolean getAksesPersetujuanBuktiPelaksanaan() {
        return aksesPersetujuanBuktiPelaksanaan;
    }

    public void setAksesPersetujuanBuktiPelaksanaan(Boolean aksesPersetujuanBuktiPelaksanaan) {
        this.aksesPersetujuanBuktiPelaksanaan = aksesPersetujuanBuktiPelaksanaan;
    }
}
