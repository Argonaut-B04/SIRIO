package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;

public interface ReminderRestService {
    Reminder createReminder(Reminder reminder);

    Reminder getById(int id_reminder);

    Reminder updateReminder(int id_reminder, Reminder reminder);

    void deleteReminder(int id_reminder);
}
