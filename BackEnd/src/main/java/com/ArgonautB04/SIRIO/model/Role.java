package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String namaRole;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Employee> employeeList;

    @OneToOne(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    private AccessPermissions accessPermissions;

    public Role() {
    }

    public Role(@NotNull @Size(max = 50) String namaRole) {
        this.namaRole = namaRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNamaRole() {
        return namaRole;
    }

    public void setNamaRole(String namaRole) {
        this.namaRole = namaRole;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public AccessPermissions getAccessPermissions() {
        return accessPermissions;
    }

    public void setAccessPermissions(AccessPermissions accessPermissions) {
        this.accessPermissions = accessPermissions;
    }
}
