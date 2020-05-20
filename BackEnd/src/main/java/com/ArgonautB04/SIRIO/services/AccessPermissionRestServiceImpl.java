package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.AccessPermissions;
import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.repository.AccessPermissionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessPermissionRestServiceImpl
        implements AccessPermissionRestService {

    /**
     * Access Permission database bind.
     */
    @Autowired
    private AccessPermissionDB accessPermissionDB;

    /**
     * Get Access Permission object from database by Role object.
     *
     * @param role Role object from other source
     * @return Access Permission object from database
     */
    @Override
    public final AccessPermissions getPermission(final Role role) {
        return accessPermissionDB.findByRole(role);
    }

}
