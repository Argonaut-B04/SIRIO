package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.AccessPermissions;
import com.argonautb04.sirio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessPermissionDB extends JpaRepository<AccessPermissions, Integer> {

    /**
     * Find Access Permission by Role.
     *
     * @param role Role object from other source
     * @return Access Permission object
     */
    AccessPermissions findByRole(Role role);
}
