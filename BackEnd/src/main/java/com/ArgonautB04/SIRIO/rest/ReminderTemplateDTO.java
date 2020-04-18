package com.ArgonautB04.SIRIO.rest;

public class ReminderTemplateDTO {
    private Integer id;
    private Integer idReminder;
    private String subject;
    private String content;
    private String effectArea;

    public ReminderTemplateDTO() {
    }

    public ReminderTemplateDTO(Integer id, Integer idReminder, String subject, String content, String effectArea) {
        this.id = id;
        this.idReminder = idReminder;
        this.subject = subject;
        this.content = content;
        this.effectArea = effectArea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIdReminder() {
        return idReminder;
    }

    public void setIdReminder(Integer idReminder) {
        this.idReminder = idReminder;
    }

    public String getEffectArea() {
        return effectArea;
    }

    public void setEffectArea(String effectArea) {
        this.effectArea = effectArea;
    }
}
