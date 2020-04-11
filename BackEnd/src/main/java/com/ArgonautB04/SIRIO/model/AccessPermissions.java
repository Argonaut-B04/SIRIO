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
}
