package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.ReminderTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReminderTemplateDB extends JpaRepository<ReminderTemplate, Integer> {
    Optional<ReminderTemplate> findBySubjectsAndBody(String subjects, String body);

    ReminderTemplate findByGlobal(boolean global);
}
