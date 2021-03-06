package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.Rekomendasi;
import com.argonautb04.sirio.model.Reminder;
import com.argonautb04.sirio.model.ReminderTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReminderRestService {
    Reminder buatReminder(Reminder reminder);

    Reminder getById(int idReminder);

    Optional<Reminder> getOptionalById(int idReminder);

    List<Reminder> getAll();

    List<Reminder> getByDay(LocalDate date, LocalDate secondDate);

    List<Reminder> getByRekomendasi(Rekomendasi rekomendasi);

    void hapusReminder(int idReminder);

    void ubahTemplateReminder(Reminder reminder);

    void telahTerkirim(Reminder reminder);

    Optional<Reminder> isExistByIdReminderDanPembuat(int idReminder, Rekomendasi rekomendasi, Employee pembuat);

    void simpanReminder(Reminder akanDiperbarui);

    Reminder validateExistById(int idReminder);

    void generateDefaultReminder(Employee employee, Rekomendasi rekomendasi);
}
