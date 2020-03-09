package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ReminderRestServiceImpl implements ReminderRestService {

    @Autowired
    private ReminderRepo reminderRepo;

    @Override
    public Reminder createReminder(Reminder reminder) {
        return reminderRepo.save(reminder);
    }

    @Override
    public Reminder getById(int id_reminder) {
        Optional<Reminder> reminder = reminderRepo.findById(id_reminder);
        if (reminder.isPresent()) return reminder.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Reminder> getAll() {
        return reminderRepo.findAll();
    }

    @Override
    public Reminder updateReminder(int id_reminder, Reminder reminder) {
        Reminder target = getById(id_reminder);
        target.setPembuat(reminder.getPembuat());
        target.setTanggal_pengiriman(reminder.getTanggal_pengiriman());
        return reminderRepo.save(target);
    }

    @Override
    public void deleteReminder(int id_reminder) {
        reminderRepo.deleteById(id_reminder);
    }
}
