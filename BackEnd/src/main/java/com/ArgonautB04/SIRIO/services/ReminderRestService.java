package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRestService {
    Reminder buatReminder(Reminder reminder);

    Reminder getById(int idReminder);

    List<Reminder> getAll();

    List<Reminder> getByDay(LocalDate date, LocalDate secondDate);

    void hapusReminder(int idReminder);

    Boolean isExistById(int idReminder);

    Reminder ubahReminder(int idReminder, LocalDate tanggalDate);
}
