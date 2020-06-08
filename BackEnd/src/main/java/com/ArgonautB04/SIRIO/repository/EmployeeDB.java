package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT * FROM employee WHERE id_employee NOT IN (SELECT pemilik FROM kantor_cabang) AND role = 6", nativeQuery = true)
    List<Employee> findBM();

}
