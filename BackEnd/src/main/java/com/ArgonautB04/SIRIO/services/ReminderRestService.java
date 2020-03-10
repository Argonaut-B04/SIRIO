package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;

import java.util.List;

public interface ReminderRestService {
    Reminder buatReminder(Reminder reminder);

    Reminder getById(int idReminder);

    List<Reminder> getAll();

    Reminder ubahReminder(int idReminder, Reminder reminder);

    void hapusReminder(int idReminder);
}
