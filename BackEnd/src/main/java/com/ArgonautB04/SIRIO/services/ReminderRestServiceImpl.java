package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderTemplate;
import com.ArgonautB04.SIRIO.repository.ReminderDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    private ReminderTemplateRestService reminderTemplateRestService;

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
    public List<Reminder> getByReminderTemplate(ReminderTemplate reminderTemplate) {
        return reminderDB.findAllByReminderTemplate(reminderTemplate);
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
    public Optional<Reminder> isExistByIdReminderDanPembuat(int idReminder, Rekomendasi rekomendasi, Employee pembuat) {
        return reminderDB.findByIdReminderAndRekomendasiAndPembuat(idReminder, rekomendasi, pembuat);
    }

    @Override
    public void simpanReminder(Reminder akanDiperbarui) {
        reminderDB.save(akanDiperbarui);
    }

    @Override
    public Reminder validateExistById(int idReminder) {
        Optional<Reminder> target = reminderDB.findById(idReminder);
        if (target.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Reminder dengan ID " + idReminder + " tidak ditemukan!"
            );
        } else {
            return target.get();
        }
    }

    @Override
    public void generateDefaultReminder(Employee employee, Rekomendasi rekomendasi) {
        // hapus reminder yang lama
        List<Reminder> old_list = getByRekomendasi(rekomendasi);
        reminderDB.deleteInBatch(old_list);

        LocalDate tenggatWaktu = rekomendasi.getTenggatWaktu();
        LocalDate today = LocalDate.now();

        // ambil template yang sesuai
        ReminderTemplate reminderTemplate;
        if (rekomendasi.getReminderTemplatePilihan() != null) {
            reminderTemplate = rekomendasi.getReminderTemplatePilihan();
        } else if (employee.getReminderTemplatePilihan() != null) {
            reminderTemplate = employee.getReminderTemplatePilihan();
        } else {
            reminderTemplate = reminderTemplateRestService.getGlobal();
        }

        LocalDate mingguAkhir = tenggatWaktu.minusWeeks(1);
        LocalDate hari3 = tenggatWaktu.minusDays(3);
        LocalDate hari1 = tenggatWaktu.minusDays(1);

        if (mingguAkhir.compareTo(today) > 0) {
            buatReminder(
                    new Reminder(
                            mingguAkhir,
                            employee,
                            rekomendasi,
                            reminderTemplate
                    )
            );
        }
        if (hari3.compareTo(today) > 0) {
            buatReminder(
                    new Reminder(
                            hari3,
                            employee,
                            rekomendasi,
                            reminderTemplate
                    )
            );
        }
        if (hari1.compareTo(today) > 0) {
            buatReminder(
                    new Reminder(
                            hari1,
                            employee,
                            rekomendasi,
                            reminderTemplate
                    )
            );
        }
    }

    @Override
    public void hapusReminder(int idReminder) {
        reminderDB.deleteById(idReminder);
    }

    @Override
    public void ubahTemplateReminder(Reminder reminder) {
        Reminder reminder1 = reminderDB.findById(reminder.getIdReminder()).get();
        reminder1.setReminderTemplate(reminder.getReminderTemplate());
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
