package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.repository.RoleRepo;
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
    private RoleRepo roleRepo;

    @Override
    public Role createRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role getById(int id_role) {
        Optional<Role> role = roleRepo.findById(id_role);
        if (role.isPresent()) return role.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role updateRole(int id_role, Role role) {
        Role target = getById(id_role);
        target.setNama_role(role.getNama_role());
        return roleRepo.save(target);
    }

    @Override
    public void deleteRole(int id_role) {
        roleRepo.deleteById(id_role);
    }
}
