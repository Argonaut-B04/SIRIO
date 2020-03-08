package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder, Integer> {
}
