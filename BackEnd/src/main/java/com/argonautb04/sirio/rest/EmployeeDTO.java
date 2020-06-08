package com.argonautb04.sirio.rest;

public class EmployeeDTO {

    private Integer id;
    private String nama;
    private String password;
    private String newPassword;
    private String noHp;
    private String email;
    private Integer idRole;
    private String username;
    private String jabatan;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(final String jabatan) {
        this.jabatan = jabatan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(final String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(final String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(final Integer idRole) {
        this.idRole = idRole;
    }

}
