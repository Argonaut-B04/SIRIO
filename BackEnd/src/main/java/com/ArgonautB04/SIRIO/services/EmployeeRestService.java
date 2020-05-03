package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import org.springframework.dao.DataIntegrityViolationException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRestService {
    Employee buatEmployee(Employee employee);

    Employee getById(int idEmployee);

    Optional<Employee> getByUsername(String username);

    List<Employee> getAll();

    Employee ubahEmployee(int idEmployee, Employee employee);

    Employee nonaktifkanEmployee(int idEmployee);

    Employee aktifkanEmployee(int idEmployee);

    void hapusEmployee(int idEmployee) throws DataIntegrityViolationException;

    Employee validateEmployeeExistByPrincipal(Principal principal);

    Employee validateEmployeeExistById(Integer id);

    void validateRolePermission(Employee employee, String requestedPermissions);

    void simpanPerubahan(Employee employee);
}
