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

    enum Status {
        AKTIF, NONAKTIF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_employee;

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
    @Size(max = 30)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false)
    private String no_hp;

    @NotNull
    @Size(max = 30)
    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id_role", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Role role;

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
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

    public void setStatus(String statusString) {
        this.status = Status.valueOf("statusString");
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

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
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
}
