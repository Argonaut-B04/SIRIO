package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;

public interface EmployeeRestService {
    Employee createEmployee(Employee employee);
    Employee getById(int id_employee);
    Employee updateEmployee(int id_employee, Employee employee);
    void deactivateEmployee(int id_employee);
    void activateEmployee(int id_employee);
}
