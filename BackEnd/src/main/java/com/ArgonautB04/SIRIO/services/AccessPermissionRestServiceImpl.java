package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.AccessPermissions;
import com.argonautb04.sirio.model.Role;
import com.argonautb04.sirio.repository.AccessPermissionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessPermissionRestServiceImpl implements AccessPermissionRestService {

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
