package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReminderMailFormatDB extends JpaRepository<ReminderMailFormat, Integer> {
    Optional<ReminderMailFormat> findBySubjectsAndMailFormat(String subjects, String mailFormat);

    ReminderMailFormat findByGlobal(boolean global);
}
