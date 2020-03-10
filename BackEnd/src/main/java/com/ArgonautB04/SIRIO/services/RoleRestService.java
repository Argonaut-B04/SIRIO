package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Role;

import java.util.List;

public interface RoleRestService {
    Role createRole(Role role);

    Role getById(int idRole);

    List<Role> getAll();

    Role updateRole(int idRole, Role role);

    void deleteRole(int idRole);
}
