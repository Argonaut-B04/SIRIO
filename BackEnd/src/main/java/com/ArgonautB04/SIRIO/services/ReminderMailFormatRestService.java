package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.ReminderMailFormat;

public interface ReminderMailFormatRestService {
    ReminderMailFormat getGlobal();

    ReminderMailFormat ambilAtauBuatTemplate(ReminderMailFormat reminderMailFormat);

    void setGlobal(ReminderMailFormat reminderMailFormat);
}
