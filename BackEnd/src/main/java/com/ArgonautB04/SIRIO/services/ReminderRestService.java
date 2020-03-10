package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;

import java.util.List;

public interface ReminderRestService {
    Reminder createReminder(Reminder reminder);

    Reminder getById(int idReminder);

    List<Reminder> getAll();

    Reminder updateReminder(int idReminder, Reminder reminder);

    void deleteReminder(int idReminder);
}
