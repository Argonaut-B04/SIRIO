package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
public class Reminder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReminder;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @Column(nullable = false)
    private Date tanggalPengiriman;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(int idReminder) {
        this.idReminder = idReminder;
    }

    public Date getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(Date tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
