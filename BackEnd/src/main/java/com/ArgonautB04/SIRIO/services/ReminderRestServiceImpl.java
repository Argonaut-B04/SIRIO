package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import com.ArgonautB04.SIRIO.repository.ReminderDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ReminderRestServiceImpl implements ReminderRestService {

    @Autowired
    private ReminderDB reminderDB;

    @Override
    public Reminder buatReminder(Reminder reminder) {
        return reminderDB.save(reminder);
    }

    @Override
    public Reminder getById(int idReminder) {
        Optional<Reminder> reminder = getOptionalById(idReminder);
        if (reminder.isPresent()) return reminder.get();
        else throw new NoSuchElementException();
    }

    @Override
    public Optional<Reminder> getOptionalById(int idReminder) {
        return reminderDB.findById(idReminder);
    }

    @Override
    public List<Reminder> getAll() {
        return reminderDB.findAll();
    }

    @Override
    public List<Reminder> getByDay(LocalDate date, LocalDate secondDate) {
        return reminderDB.findAllByTanggalPengirimanBetween(date, secondDate);
    }

    @Override
    public List<Reminder> getByReminderMailFormat(ReminderMailFormat reminderMailFormat) {
        return reminderDB.findAllByReminderMailFormat(reminderMailFormat);
    }

    @Override
    public Reminder ubahReminder(int idReminder, LocalDate tanggalReminder) {
        Reminder target = getById(idReminder);
        target.setTanggalPengiriman(tanggalReminder);
        return reminderDB.save(target);
    }

    @Override
    public void telahTerkirim(Reminder reminder) {
        Reminder reminder1 = reminderDB.findById(reminder.getIdReminder()).get();
        reminder1.setTerkirim(true);
        reminderDB.save(reminder1);
    }

    @Override
    public void hapusReminder(int idReminder) {
        reminderDB.deleteById(idReminder);
    }

    @Override
    public void ubahTemplateReminder(Reminder reminder) {
        Reminder reminder1 = reminderDB.findById(reminder.getIdReminder()).get();
        reminder1.setReminderMailFormat(reminder.getReminderMailFormat());
        reminderDB.save(reminder1);
    }

    @Override
    public Boolean isExistById(int idReminder) {
        return reminderDB.findById(idReminder).isPresent();
    }

    @Override
    public List<Reminder> getByRekomendasi(Rekomendasi rekomendasi) {
        return reminderDB.findAllByRekomendasi(rekomendasi);
    }
}
