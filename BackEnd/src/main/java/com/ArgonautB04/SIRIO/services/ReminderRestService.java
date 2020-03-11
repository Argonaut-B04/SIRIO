package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRestService {
    Reminder buatReminder(Reminder reminder);

    Reminder getById(int idReminder);

    List<Reminder> getAll();

    Reminder ubahReminder(int idReminder, LocalDate tanggalReminder);

    void hapusReminder(int idReminder);

    List<Reminder> getByRekomendasi(Rekomendasi rekomendasi);
}
