package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Role;

import java.util.List;

public interface RoleRestService {
    Role buatRole(Role role);

    Role getById(int idRole);

    List<Role> getAll();

    Role ubahRole(int idRole, Role role);

    void hapusRole(int idRole);
}
