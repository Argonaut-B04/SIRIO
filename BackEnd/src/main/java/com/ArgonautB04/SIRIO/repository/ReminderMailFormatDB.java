package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderMailFormatDB extends JpaRepository<ReminderMailFormat, Integer> {
}
