package com.ArgonautB04.SIRIO.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class ReminderMailFormat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReminderMailFormat;

    @NotNull
    @Size(max = 75)
    @Column
    private String subjects;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String mailFormat;

    @Column
    private Boolean global = false;

    public ReminderMailFormat() {
    }

    public ReminderMailFormat(@NotNull @Size(max = 75) String subjects, String mailFormat) {
        this.subjects = subjects;
        this.mailFormat = mailFormat;
    }

    public int getIdReminderMailFormat() {
        return idReminderMailFormat;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public void setIdReminderMailFormat(int idReminderMailFormat) {
        this.idReminderMailFormat = idReminderMailFormat;
    }

    public String getMailFormat() {
        return mailFormat;
    }

    public void setMailFormat(String mailFormat) {
        this.mailFormat = mailFormat;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }
}
