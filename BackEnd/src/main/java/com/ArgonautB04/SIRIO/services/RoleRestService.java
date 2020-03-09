package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Role;

import java.util.List;

public interface RoleRestService {
    Role createRole(Role role);

    Role getById(int id_role);

    List<Role> getAll();

    Role updateRole(int id_role, Role role);

    void deleteRole(int id_role);
}
