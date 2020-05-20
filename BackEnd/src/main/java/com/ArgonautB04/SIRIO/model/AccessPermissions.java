package com.ArgonautB04.SIRIO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Access Permission object that binds to Role.
 */
@Entity
@Table
public class AccessPermissions implements Serializable {

    /**
     * Primary Role object.
     */
    @OneToOne
    @MapsId
    private Role role;

    /**
     * Id for access permissions should be the same as Role id.
     */
    @Id
    @Column(name = "idPermission")
    private Integer idPermission;

    /**
     * Permission to access Risk Rating.
     */
    @Column
    private Boolean aksesRiskRating = false;

    /**
     * Permission to change Risk Rating.
     */
    @Column
    private Boolean ubahRiskRating = false;

    /**
     * Permission to access Risk Level.
     */
    @Column
    private Boolean aksesRiskLevel = false;

    /**
     * Permission to change risk level.
     */
    @Column
    private Boolean ubahRiskLevel = false;

    /**
     * Permission to change reminder.
     */
    @Column
    private Boolean ubahReminder = false;

    /**
     * Permission to access Risk Table.
     */
    @Column
    private Boolean aksesTabelRisiko = false;

    /**
     * Permission to access Risk.
     */
    @Column
    private Boolean aksesRisiko = false;

    /**
     * Permission to add new Risk.
     */
    @Column
    private Boolean aksesTambahRisiko = false;

    /**
     * Permission to change Risk.
     */
    @Column
    private Boolean aksesUbahRisiko = false;

    /**
     * Permission to delete Risk.
     */
    @Column
    private Boolean aksesHapusRisiko = false;

    /**
     * Permission to change Risk Hierarchy.
     */
    @Column
    private Boolean aksesUbahHierarki = false;

    /**
     * Permission to access Recommendation Table.
     */
    @Column
    private Boolean aksesTabelRekomendasi = false;

    /**
     * Permission to access Recommendation.
     */
    @Column
    private Boolean aksesRekomendasi = false;

    /**
     * Permission to access Bukti Pelaksanaan.
     */
    @Column
    private Boolean aksesBuktiPelaksanaan = false;

    /**
     * Permission to add Bukti Pelaksanaan.
     */
    @Column
    private Boolean aksesTambahBuktiPelaksanaan = false;

    /**
     * Permission to change Bukti Pelaksanaan.
     */
    @Column
    private Boolean aksesUbahBuktiPelaksanaan = false;

    /**
     * Permission to give approval to Bukti Pelaksanaan.
     */
    @Column
    private Boolean aksesPersetujuanBuktiPelaksanaan = false;

    /**
     * Permission to change Recommendation Deadline.
     */
    @Column
    private Boolean aturTenggatWaktu = false;

    /**
     * Permission to access Staff Dashboard.
     */
    @Column
    private Boolean aksesDashboardStaff = false;

    /**
     * Permission to access Branch Office Dashboard.
     */
    @Column
    private Boolean aksesDashboardKC = false;


    /**
     * Empty Constructor.
     */
    public AccessPermissions() {
    }

    /**
     * Constructor for initial role and initial full access.
     *
     * @param primaryRole Bind to this Role object
     * @param trueDefault Set all access permission to true.
     */
    public AccessPermissions(final Role primaryRole,
                             final boolean trueDefault) {
        this.role = primaryRole;
        if (trueDefault) {
            this.aksesRiskRating = true;
            this.ubahRiskRating = true;
            this.aksesRiskLevel = true;
            this.ubahRiskLevel = true;
            this.ubahReminder = true;
            this.aksesTabelRisiko = true;
            this.aksesRisiko = true;
            this.aksesTambahRisiko = true;
            this.aksesUbahRisiko = true;
            this.aksesHapusRisiko = true;
            this.aksesUbahHierarki = true;
            this.aksesTabelRekomendasi = true;
            this.aksesRekomendasi = true;
            this.aksesBuktiPelaksanaan = true;
            this.aksesTambahBuktiPelaksanaan = true;
            this.aksesUbahBuktiPelaksanaan = true;
            this.aksesPersetujuanBuktiPelaksanaan = true;
            this.aturTenggatWaktu = true;
            this.aksesDashboardStaff = true;
            this.aksesDashboardKC = true;
        }

    }

    /**
     * Getter for Branch Office Dashboard access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesDashboardStaff() {
        return aksesDashboardStaff;
    }

    /**
     * Getter for Branch Office Dashboard access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesTabelRisiko() {
        return aksesTabelRisiko;
    }

    /**
     * Getter for Risk access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesRisiko() {
        return aksesRisiko;
    }

    /**
     * Getter for add Risk access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesTambahRisiko() {
        return aksesTambahRisiko;
    }

    /**
     * Getter for change Risk access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesUbahRisiko() {
        return aksesUbahRisiko;
    }

    /**
     * Getter for delete Risk access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesHapusRisiko() {
        return aksesHapusRisiko;
    }

    /**
     * Getter for change Risk hierarchy access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesUbahHierarki() {
        return aksesUbahHierarki;
    }

    /**
     * Getter for Risk Rating access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesRiskRating() {
        return aksesRiskRating;
    }

    /**
     * Getter for change Risk Rating access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getUbahRiskRating() {
        return ubahRiskRating;
    }

    /**
     * Getter for Risk Level access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesRiskLevel() {
        return aksesRiskLevel;
    }

    /**
     * Getter for Change Risk Level access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getUbahRiskLevel() {
        return ubahRiskLevel;
    }

    /**
     * Getter for Recommendation Table access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesTabelRekomendasi() {
        return aksesTabelRekomendasi;
    }

    /**
     * Getter for Bukti Pelaksanaan access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesBuktiPelaksanaan() {
        return aksesBuktiPelaksanaan;
    }

    /**
     * Getter for add Bukti Pelaksanaan access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesTambahBuktiPelaksanaan() {
        return aksesTambahBuktiPelaksanaan;
    }

    /**
     * Getter for change Bukti Pelaksanaan access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesUbahBuktiPelaksanaan() {
        return aksesUbahBuktiPelaksanaan;
    }

    /**
     * Getter for approval Bukti Pelaksanaan access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAksesPersetujuanBuktiPelaksanaan() {
        return aksesPersetujuanBuktiPelaksanaan;
    }

    /**
     * Getter for set Recommendation Deadline access.
     *
     * @return boolean true if has access, false otherwise.
     */
    public Boolean getAturTenggatWaktu() {
        return aturTenggatWaktu;
    }

    /**
     * Getter for Permission ID.
     *
     * @return id for this object.
     */
    public Integer getIdPermission() {
        return idPermission;
    }

    /**
     * Setter for Permission id.
     *
     * @param permissionId to set.
     */
    public void setIdPermission(final Integer permissionId) {
        this.idPermission = permissionId;
    }
}
