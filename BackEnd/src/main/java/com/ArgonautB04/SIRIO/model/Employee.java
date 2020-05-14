package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class Employee implements Serializable {

    public enum Status {
        AKTIF, NONAKTIF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmployee;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String nama;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(max = 70)
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Size(max = 20)
    @Column
    private String noHp;

    @NotNull
    @Size(max = 30)
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(max = 30)
    @Column(nullable = false)
    private String jabatan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "idRole", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "reminderTemplate", referencedColumnName = "idReminderTemplate")
    @JsonIgnore
    private ReminderTemplate reminderTemplatePilihan;

    public Employee() {
    }

    public Employee(@NotNull @Size(max = 50) String nama, @NotNull Status status, @NotNull @Size(max = 20) String username, @NotNull @Size(max = 70) String password, @Size(max = 20) String noHp, @NotNull @Size(max = 30) String email, @NotNull @Size(max = 30) String jabatan, Role role) {
        this.nama = nama;
        this.status = status;
        this.username = username;
        this.password = password;
        this.noHp = noHp;
        this.email = email;
        this.jabatan = jabatan;
        this.role = role;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ReminderTemplate getReminderTemplatePilihan() {
        return reminderTemplatePilihan;
    }

    public void setReminderTemplatePilihan(ReminderTemplate reminderTemplatePilihan) {
        this.reminderTemplatePilihan = reminderTemplatePilihan;
    }
}
