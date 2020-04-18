package com.ArgonautB04.SIRIO.scheduled;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import com.ArgonautB04.SIRIO.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Configuration
@EnableScheduling
public class MailScheduler {

    public static boolean startMe = false;

    @Autowired
    ReminderRestService reminderRestService;

    @Autowired
    EmployeeRestService employeeRestService;

    @Autowired
    RekomendasiRestService rekomendasiRestService;

    @Autowired
    ReminderMailFormatRestService reminderMailFormatRestService;

    @Autowired
    EmailRestService emailRestService;

    Logger logger = LoggerFactory.getLogger(MailScheduler.class);

    @Scheduled(fixedRate = 3600000)
    public void testSSchedule() {
        if (!startMe) return;
        Calendar todayCalender = Calendar.getInstance();
        todayCalender.set(Calendar.HOUR_OF_DAY, 0);
        todayCalender.set(Calendar.MINUTE, 0);
        todayCalender.set(Calendar.SECOND, 0);
        todayCalender.add(Calendar.HOUR_OF_DAY, -1);
        LocalDate today = LocalDateTime.ofInstant(todayCalender.toInstant(), todayCalender.getTimeZone().toZoneId()).toLocalDate();

        Calendar tommorowData = Calendar.getInstance();
        tommorowData.set(Calendar.HOUR_OF_DAY, 0);
        tommorowData.set(Calendar.MINUTE, 0);
        tommorowData.set(Calendar.SECOND, 0);
        tommorowData.add(Calendar.HOUR_OF_DAY, -1);
        tommorowData.add(Calendar.DATE, 1);
        LocalDate tommorow = LocalDateTime.ofInstant(tommorowData.toInstant(), tommorowData.getTimeZone().toZoneId()).toLocalDate();

        logger.info("Mengirim reminders untuk tanggal " + today);

        List<Reminder> reminders = reminderRestService.getByDay(today, tommorow);
        if (reminders.size() == 0) logger.warn("Tidak ada reminder terdaftar untuk hari ini");
        for (Reminder reminder : reminders) {
            if (reminder.isTerkirim()) continue;
            Employee penerima =
                    reminder.getRekomendasi()
                            .getKomponenPemeriksaan()
                            .getHasilPemeriksaan()
                            .getTugasPemeriksaan()
                            .getKantorCabang()
                            .getPemilik();

            ReminderMailFormat reminderMailFormatSingleReminder = reminder.getReminderMailFormat();
            if (reminderMailFormatSingleReminder == null) {
                reminderMailFormatSingleReminder = reminderMailFormatRestService.getGlobal();
            }

            emailRestService.sendEmail(penerima.getEmail(), reminderMailFormatSingleReminder.getSubjects(), reminderMailFormatSingleReminder.getMailFormat());
            reminderRestService.telahTerkirim(reminder);
            logger.info("Reminder terkirim kepada " + penerima.getNama());
        }
        logger.info("Pengiriman reminders selesai");
    }
}
