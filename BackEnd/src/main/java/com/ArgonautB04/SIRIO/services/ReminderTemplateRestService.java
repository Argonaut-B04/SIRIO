package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.ReminderTemplate;

public interface ReminderTemplateRestService {
    ReminderTemplate getGlobal();

    ReminderTemplate ambilAtauBuatTemplate(ReminderTemplate reminderTemplate);

    void setGlobal(ReminderTemplate reminderTemplate);
}
