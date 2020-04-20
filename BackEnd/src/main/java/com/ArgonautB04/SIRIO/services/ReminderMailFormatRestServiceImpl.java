package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import com.ArgonautB04.SIRIO.repository.ReminderMailFormatDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReminderMailFormatRestServiceImpl implements ReminderMailFormatRestService {

    @Autowired
    ReminderMailFormatDB reminderMailFormatDB;

    @Override
    public ReminderMailFormat getGlobal() {
        return reminderMailFormatDB.findByGlobal(true);
    }

    @Override
    public void setGlobal(ReminderMailFormat reminderMailFormat) {
        ReminderMailFormat currentGlobal = getGlobal();
        reminderMailFormatDB.delete(currentGlobal);

        ReminderMailFormat newGlobal = ambilAtauBuatTemplate(reminderMailFormat);
        newGlobal.setGlobal(true);
        reminderMailFormatDB.save(newGlobal);
    }

    @Override
    public ReminderMailFormat ambilAtauBuatTemplate(ReminderMailFormat reminderMailFormat) {
        Optional<ReminderMailFormat> template = reminderMailFormatDB.findBySubjectsAndMailFormat(
                reminderMailFormat.getSubjects(),
                reminderMailFormat.getMailFormat()
        );
        return template.orElseGet(() -> reminderMailFormatDB.save(reminderMailFormat));
    }
}
