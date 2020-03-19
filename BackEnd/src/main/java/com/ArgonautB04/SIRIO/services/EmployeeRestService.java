package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;

import java.util.List;

public interface EmployeeRestService {
    Employee buatEmployee(Employee employee);

    Employee getById(int idEmployee);

    List<Employee> getAll();

    Employee ubahEmployee(int idEmployee, Employee employee);

    Employee nonaktifkanEmployee(int idEmployee);

    Employee aktifkanEmployee(int idEmployee);
}
