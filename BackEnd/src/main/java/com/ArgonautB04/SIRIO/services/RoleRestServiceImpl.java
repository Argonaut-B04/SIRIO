package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RoleRestServiceImpl implements RoleRestService {

    @Autowired
    private RoleDB roleDB;

    @Override
    public Role buatRole(Role role) {
        return roleDB.save(role);
    }

    @Override
    public Role getById(int idRole) {
        Optional<Role> role = roleDB.findById(idRole);
        if (role.isPresent()) return role.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Role> getAll() {
        return roleDB.findAll();
    }

    @Override
    public Role ubahRole(int idRole, Role role) {
        Role target = getById(idRole);
        target.setNamaRole(role.getNamaRole());
        return roleDB.save(target);
    }

    @Override
    public void hapusRole(int idRole) {
        roleDB.deleteById(idRole);
    }
}
