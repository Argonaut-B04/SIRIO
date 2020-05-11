package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDB extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsernameAndStatus(String username, Employee.Status status);
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByIdEmployeeAndStatus(Integer id, Employee.Status status);
    List<Employee> findAllByStatus(Employee.Status status);
}
