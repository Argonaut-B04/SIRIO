package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;

import java.util.List;

public interface EmployeeRestService {
    Employee createEmployee(Employee employee);

    Employee getById(int id_employee);

    List<Employee> getAll();

    Employee updateEmployee(int id_employee, Employee employee);

    void deactivateEmployee(int id_employee);

    void activateEmployee(int id_employee);
}
