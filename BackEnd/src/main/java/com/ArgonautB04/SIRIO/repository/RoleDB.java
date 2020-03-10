package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDB extends JpaRepository<Role, Integer> {
}
