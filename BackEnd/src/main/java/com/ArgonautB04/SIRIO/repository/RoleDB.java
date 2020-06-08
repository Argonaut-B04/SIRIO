package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDB extends JpaRepository<Role, Integer> {
}
