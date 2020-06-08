package com.argonautb04.sirio.scheduled;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.Reminder;
import com.argonautb04.sirio.model.ReminderTemplate;
import com.argonautb04.sirio.services.EmailRestService;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.RekomendasiRestService;
import com.argonautb04.sirio.services.ReminderRestService;
import com.argonautb04.sirio.services.ReminderTemplateRestService;
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

    public static boolean startService = false;

    @Autowired
    ReminderRestService reminderRestService;

    @Autowired
    EmployeeRestService employeeRestService;

    @Autowired
    RekomendasiRestService rekomendasiRestService;

    @Autowired
    ReminderTemplateRestService reminderTemplateRestService;

    @Autowired
    EmailRestService emailRestService;

    Logger logger = LoggerFactory.getLogger(MailScheduler.class);

    @Scheduled(fixedRate = 3600000)
    public void testSSchedule() {
        if (!startService)
            return;
        final Calendar todayCalender = Calendar.getInstance();
        todayCalender.set(Calendar.HOUR_OF_DAY, 0);
        todayCalender.set(Calendar.MINUTE, 0);
        todayCalender.set(Calendar.SECOND, 0);
        todayCalender.add(Calendar.HOUR_OF_DAY, -1);
        final LocalDate today = LocalDateTime
                .ofInstant(todayCalender.toInstant(), todayCalender.getTimeZone().toZoneId()).toLocalDate();

        final Calendar tommorowData = Calendar.getInstance();
        tommorowData.set(Calendar.HOUR_OF_DAY, 0);
        tommorowData.set(Calendar.MINUTE, 0);
        tommorowData.set(Calendar.SECOND, 0);
        tommorowData.add(Calendar.HOUR_OF_DAY, -1);
        tommorowData.add(Calendar.DATE, 1);
        final LocalDate tommorow = LocalDateTime
                .ofInstant(tommorowData.toInstant(), tommorowData.getTimeZone().toZoneId()).toLocalDate();

        logger.info("Mengirim reminders untuk tanggal " + today);

        final List<Reminder> reminders = reminderRestService.getByDay(today, tommorow);
        if (reminders.size() == 0)
            logger.warn("Tidak ada reminder terdaftar untuk hari ini");
        for (final Reminder reminder : reminders) {
            if (reminder.isTerkirim())
                continue;
            final Employee penerima = reminder.getRekomendasi().getKomponenPemeriksaan().getHasilPemeriksaan()
                    .getTugasPemeriksaan().getKantorCabang().getPemilik();

            ReminderTemplate reminderMailFormatSingleReminder = reminder.getReminderTemplate();
            if (reminderMailFormatSingleReminder == null) {
                reminderMailFormatSingleReminder = reminderTemplateRestService.getGlobal();
            }

            emailRestService.sendEmail(penerima.getEmail(), reminderMailFormatSingleReminder.getSubjects(),
                    reminderMailFormatSingleReminder.getBody());
            reminderRestService.telahTerkirim(reminder);
            logger.info("Reminder terkirim kepada " + penerima.getNama());
        }
        logger.info("Pengiriman reminders selesai");
    }
}
