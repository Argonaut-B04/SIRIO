package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.AccessPermissions;
import com.ArgonautB04.SIRIO.model.Role;

public interface AccessPermissionRestService {
    AccessPermissions getPermission(Role role);
    Boolean roleHasPermission(Role role, String requestedAccess);
}
