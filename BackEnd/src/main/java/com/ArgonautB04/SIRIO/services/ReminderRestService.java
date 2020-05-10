package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReminderRestService {
    Reminder buatReminder(Reminder reminder);

    Reminder getById(int idReminder);

    Optional<Reminder> getOptionalById(int idReminder);

    List<Reminder> getAll();

    List<Reminder> getByDay(LocalDate date, LocalDate secondDate);

    List<Reminder> getByReminderTemplate(ReminderTemplate reminderTemplate);

    List<Reminder> getByRekomendasi(Rekomendasi rekomendasi);

    void hapusReminder(int idReminder);

    void ubahTemplateReminder(Reminder reminder);

    Boolean isExistById(int idReminder);

    Reminder ubahReminder(int idReminder, LocalDate tanggalDate);

    void telahTerkirim(Reminder reminder);

    Optional<Reminder> isExistByIdReminderDanPembuat(int idReminder, Rekomendasi rekomendasi, Employee pembuat);

    void simpanReminder(Reminder akanDiperbarui);

    Reminder validateExistById(int idReminder);

    void generateDefaultReminder(Employee employee, Rekomendasi rekomendasi);
}
