package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
public class Reminder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReminder;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @Column(nullable = false)
    private LocalDate tanggalPengiriman;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    @ManyToOne
    @JoinColumn(name = "rekomendasi", referencedColumnName = "idRekomendasi")
    @JsonIgnore
    private Rekomendasi rekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reminderMailFormat", referencedColumnName = "idReminderTemplate")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private ReminderTemplate reminderTemplate;

    @NotNull
    @Column
    private boolean terkirim = false;

    public Reminder() {
    }

    public Reminder(@NotNull LocalDate tanggalPengiriman, Employee pembuat, Rekomendasi rekomendasi,
                    ReminderTemplate reminderTemplate) {
        this.tanggalPengiriman = tanggalPengiriman;
        this.pembuat = pembuat;
        this.rekomendasi = rekomendasi;
        this.reminderTemplate = reminderTemplate;
    }

    public int getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(int idReminder) {
        this.idReminder = idReminder;
    }

    public LocalDate getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(LocalDate tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }

    public Rekomendasi getRekomendasi() {
        return rekomendasi;
    }

    public void setRekomendasi(Rekomendasi rekomendasi) {
        this.rekomendasi = rekomendasi;
    }

    public ReminderTemplate getReminderTemplate() {
        return reminderTemplate;
    }

    public void setReminderTemplate(ReminderTemplate reminderTemplate) {
        this.reminderTemplate = reminderTemplate;
    }

    public boolean isTerkirim() {
        return terkirim;
    }

    public void setTerkirim(boolean terkirim) {
        this.terkirim = terkirim;
    }
}
