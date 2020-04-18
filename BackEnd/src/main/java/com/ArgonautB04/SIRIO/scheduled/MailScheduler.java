package com.ArgonautB04.SIRIO.scheduled;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import com.ArgonautB04.SIRIO.services.*;
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

    @Scheduled(fixedRate = 3600000)
    public void testSSchedule() {
        // TODO: bikin laporan harian kalau pengiriman untuk tanggal segini udah selesai atau belum
        // Concern : kalau server di restart 2x, atau direstart diwaktu yang tidak tepat, reminder bisa saja tidak terkirim
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

        List<Reminder> reminders = reminderRestService.getByDay(today, tommorow);
        for (Reminder reminder : reminders) {
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
        }
    }
}
