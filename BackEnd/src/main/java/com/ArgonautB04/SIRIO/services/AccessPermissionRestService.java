package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.AccessPermissions;
import com.ArgonautB04.SIRIO.model.Role;

public interface AccessPermissionRestService {
    /**
     * Get Permission from Database by Role.
     *
     * @param role Role object from other source
     * @return Access Permissions object from database
     */
    AccessPermissions getPermission(Role role);
}
