package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;

import java.util.List;

public interface EmployeeRestService {
    Employee createEmployee(Employee employee);

    Employee getById(int idEmployee);

    List<Employee> getAll();

    Employee updateEmployee(int idEmployee, Employee employee);

    void deactivateEmployee(int idEmployee);

    void activateEmployee(int idEmployee);
}
