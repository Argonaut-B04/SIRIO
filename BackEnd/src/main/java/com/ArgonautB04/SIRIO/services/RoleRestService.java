package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Role;

public interface RoleRestService {
    Role createRole(Role role);
    Role getById(int id_role);
    Role updateRole(int id_role, Role role);
    void deleteRole(int id_role);
}
