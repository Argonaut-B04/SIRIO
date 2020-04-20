package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ReminderDB extends JpaRepository<Reminder, Integer> {
    List<Reminder> findAllByTanggalPengirimanBetween(@NotNull LocalDate tanggalPengiriman, @NotNull LocalDate tanggalPengiriman2);

    List<Reminder> findAllByReminderMailFormat(ReminderMailFormat reminderMailFormat);

    List<Reminder> findAllByRekomendasi(Rekomendasi rekomendasi);
}
