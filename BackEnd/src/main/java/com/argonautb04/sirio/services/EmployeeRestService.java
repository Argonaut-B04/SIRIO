package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import org.springframework.dao.DataIntegrityViolationException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRestService {
    Employee buatEmployee(Employee employee);

    Employee getById(int idEmployee);

    Optional<Employee> getByUsername(String username);

    Optional<Employee> getByEmail(String email);

    List<Employee> getAll();

    List<Employee> getAllWithNonAktif();

    Employee ubahEmployee(int idEmployee, Employee employee);

    Employee nonaktifkanEmployee(int idEmployee);

    Employee aktifkanEmployee(int idEmployee);

    void hapusEmployee(int idEmployee) throws DataIntegrityViolationException;

    Employee validateEmployeeExistByPrincipal(Principal principal);

    Employee validateEmployeeExistByUsername(String username);

    Employee validateEmployeeExistById(Integer id);

    void validateRolePermission(Employee employee, String requestedPermissions);

    void simpanPerubahan(Employee employee);

    List<Employee> getUnassignedBM();

    boolean changePassword(String username, String oldPassword, String newPassword);

    void setPassword(String username, String password);

    List<Employee> getEmptyPassword();
}
