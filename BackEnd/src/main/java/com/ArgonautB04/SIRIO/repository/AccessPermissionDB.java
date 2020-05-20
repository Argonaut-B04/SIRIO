package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.AccessPermissions;
import com.ArgonautB04.SIRIO.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessPermissionDB
        extends JpaRepository<AccessPermissions, Integer> {

    /**
     * Find Access Permission by Role.
     *
     * @param role Role object from other source
     * @return Access Permission object
     */
    AccessPermissions findByRole(Role role);
}
