package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import com.ArgonautB04.SIRIO.repository.ReminderMailFormatDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReminderMailFormatRestServiceImpl implements ReminderMailFormatRestService{

    @Autowired
    ReminderMailFormatDB reminderMailFormatDB;

    @Override
    public ReminderMailFormat getGlobal() {
        return reminderMailFormatDB.getOne(1);
    }
}
