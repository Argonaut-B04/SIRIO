package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.ReminderTemplate;

public interface ReminderTemplateRestService {
    ReminderTemplate getGlobal();

    void setGlobal(ReminderTemplate reminderTemplate);

    ReminderTemplate ambilAtauBuatTemplate(ReminderTemplate reminderTemplate);
}
