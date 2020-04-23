package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderDB extends JpaRepository<Reminder, Integer> {
    List<Reminder> findAllByTanggalPengirimanBetween(@NotNull LocalDate tanggalPengiriman, @NotNull LocalDate tanggalPengiriman2);

    List<Reminder> findAllByReminderTemplate(ReminderTemplate reminderTemplate);

    List<Reminder> findAllByRekomendasi(Rekomendasi rekomendasi);

    Optional<Reminder> findByIdReminderAndRekomendasiAndPembuat(int idReminder, Rekomendasi rekomendasi, Employee pembuat);

}
