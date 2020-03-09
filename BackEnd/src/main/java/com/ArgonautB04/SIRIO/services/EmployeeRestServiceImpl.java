package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EmployeeRestServiceImpl implements EmployeeRestService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public Employee updateEmployee(int id_employee, Employee employee) {
        Employee target = getById(id_employee);
        target.setEmail(employee.getEmail());
        target.setNama(employee.getNama());
        target.setNo_hp(employee.getNo_hp());
        target.setPassword(employee.getPassword());
        target.setRole(employee.getRole());
        target.setUsername(employee.getUsername());
        return employeeRepo.save(target);
    }

    @Override
    public Employee getById(int id_employee) {
        Optional<Employee> employee = employeeRepo.findById(id_employee);
        if (employee.isPresent()) return employee.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    @Override
    public void deactivateEmployee(int id_employee) {
        Employee employee = getById(id_employee);
        employee.setStatus("NONAKTIF");
    }

    @Override
    public void activateEmployee(int id_employee) {
        Employee employee = getById(id_employee);
        employee.setStatus("AKTIF");
    }
}
