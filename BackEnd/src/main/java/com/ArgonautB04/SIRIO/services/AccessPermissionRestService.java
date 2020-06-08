package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.AccessPermissions;
import com.argonautb04.sirio.model.Role;

public interface AccessPermissionRestService {
    /**
     * Get Permission from Database by Role.
     *
     * @param role Role object from other source
     * @return Access Permissions object from database
     */
    AccessPermissions getPermission(Role role);
}
