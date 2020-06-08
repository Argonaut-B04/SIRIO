package com.argonautb04.sirio.rest;

public class ReminderTemplateDTO {
    private Integer id;
    private Integer idReminder;
    private String subject;
    private String content;
    private String effectArea;

    public ReminderTemplateDTO() {
    }

    public ReminderTemplateDTO(final Integer id, final Integer idReminder, final String subject, final String content,
                               final String effectArea) {
        this.id = id;
        this.idReminder = idReminder;
        this.subject = subject;
        this.content = content;
        this.effectArea = effectArea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Integer getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(final Integer idReminder) {
        this.idReminder = idReminder;
    }

    public String getEffectArea() {
        return effectArea;
    }

    public void setEffectArea(final String effectArea) {
        this.effectArea = effectArea;
    }
}
