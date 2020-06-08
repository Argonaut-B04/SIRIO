package com.argonautb04.sirio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class ReminderTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReminderTemplate;

    @NotNull
    @Size(max = 75)
    @Column
    private String subjects;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private Boolean global = false;

    public ReminderTemplate() {
    }

    public ReminderTemplate(@NotNull @Size(max = 75) String subjects, String mailFormat) {
        this.subjects = subjects;
        this.body = mailFormat;
    }

    public int getIdReminderTemplate() {
        return idReminderTemplate;
    }

    public void setIdReminderTemplate(int idReminderMailFormat) {
        this.idReminderTemplate = idReminderMailFormat;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String mailFormat) {
        this.body = mailFormat;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }
}
