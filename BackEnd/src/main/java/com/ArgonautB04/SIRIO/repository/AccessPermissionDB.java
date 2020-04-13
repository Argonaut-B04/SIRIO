package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.AccessPermissions;
import com.ArgonautB04.SIRIO.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessPermissionDB extends JpaRepository<AccessPermissions, Integer> {
    AccessPermissions findByRole(Role role);
}
