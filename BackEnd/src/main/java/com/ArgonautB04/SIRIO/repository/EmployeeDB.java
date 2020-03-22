package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDB extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username);
}
