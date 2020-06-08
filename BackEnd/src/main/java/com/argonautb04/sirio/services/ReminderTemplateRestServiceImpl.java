package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.ReminderTemplate;
import com.argonautb04.sirio.repository.ReminderTemplateDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReminderTemplateRestServiceImpl implements ReminderTemplateRestService {

    @Autowired
    ReminderTemplateDB reminderTemplateDB;

    @Override
    public ReminderTemplate getGlobal() {
        return reminderTemplateDB.findByGlobal(true);
    }

    @Override
    public void setGlobal(ReminderTemplate reminderTemplate) {
        ReminderTemplate currentGlobal = getGlobal();
        currentGlobal.setBody(reminderTemplate.getBody());
        currentGlobal.setSubjects(reminderTemplate.getSubjects());

        reminderTemplateDB.save(currentGlobal);
    }

    @Override
    public ReminderTemplate ambilAtauBuatTemplate(ReminderTemplate reminderTemplate) {
        Optional<ReminderTemplate> template = reminderTemplateDB.findBySubjectsAndBody(reminderTemplate.getSubjects(),
                reminderTemplate.getBody());
        return template.orElseGet(() -> reminderTemplateDB.save(reminderTemplate));
    }
}
