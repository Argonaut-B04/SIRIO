package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;

import java.util.Date;
import java.util.List;

public interface ReminderRestService {
    Reminder buatReminder(Reminder reminder);

    Reminder getById(int idReminder);

    List<Reminder> getAll();

    Reminder ubahReminder(int idReminder, Date tanggalReminder);

    void hapusReminder(int idReminder);

    Boolean isExistById(int idReminder);
}
