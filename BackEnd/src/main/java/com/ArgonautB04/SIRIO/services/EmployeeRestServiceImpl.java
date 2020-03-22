package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.repository.EmployeeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EmployeeRestServiceImpl implements EmployeeRestService {

    @Autowired
    private EmployeeDB employeeDb;

    @Override
    public Employee buatEmployee(Employee employee) {
        employee.setPassword(encrypt(employee.getPassword()));
        return employeeDb.save(employee);
    }

    private String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public Employee ubahEmployee(int idEmployee, Employee employee) {
        Employee target = getById(idEmployee);
        target.setEmail(employee.getEmail());
        target.setNama(employee.getNama());
        target.setNoHp(employee.getNoHp());
        target.setPassword(employee.getPassword());
        target.setRole(employee.getRole());
        target.setUsername(employee.getUsername());
        return employeeDb.save(target);
    }

    @Override
    public Employee getById(int idEmployee) {
        Optional<Employee> employee = employeeDb.findById(idEmployee);
        if (employee.isPresent()) return employee.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Employee> getAll() {
        return employeeDb.findAll();
    }

    @Override
    public Employee getByUsername(String username) {
        return employeeDb.findByUsername(username);
    }

    @Override
    public Employee nonaktifkanEmployee(int idEmployee) {
        Employee employee = getById(idEmployee);
        employee.setStatus(Employee.Status.NONAKTIF);
        return employee;
    }

    @Override
    public Employee aktifkanEmployee(int idEmployee) {
        Employee employee = getById(idEmployee);
        employee.setStatus(Employee.Status.AKTIF);
        return employee;
    }
}
