package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.EmployeeDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RoleRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeRestController {

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private RoleRestService roleRestService;

    /**
     * Menambah employee baru
     *
     * @param employeeDTO data transfer object untuk employee yang akan ditambah
     * @return employee yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<Employee> tambahEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        BaseResponse<Employee> response = new BaseResponse<>();
        if (employeeRestService.getByUsername(employeeDTO.getUsername()).isPresent())
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username " + employeeDTO.getUsername() + " sudah ada pada database!"
            );

        Employee employee = new Employee();
        employee.setStatus(Employee.Status.AKTIF);
        employee.setEmail(employeeDTO.getEmail());
        employee.setNama(employeeDTO.getNama());
        employee.setNoHp(employeeDTO.getNoHp());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());

        try {
            Role role = roleRestService.getById(employeeDTO.getIdRole());
            employee.setRole(role);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role dengan ID " + employeeDTO.getIdRole() + " tidak ditemukan!"
            );
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(employeeRestService.buatEmployee(employee));
        return response;
    }

    /**
     * Mengubah employee
     *
     * @param employeeDTO data transfer object untuk employee yang akan diubah
     * @return employee yang telah diperbarui
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<Employee> ubahEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        BaseResponse<Employee> response = new BaseResponse<>();
        Employee employee = employeeRestService.getById(employeeDTO.getId());
        employee.setNama(employeeDTO.getNama());
        employee.setEmail(employeeDTO.getEmail());
        employee.setNama(employeeDTO.getNama());
        employee.setNoHp(employeeDTO.getNoHp());

        try {
            Role role = roleRestService.getById(employeeDTO.getIdRole());
            employee.setRole(role);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role dengan ID " + employeeDTO.getIdRole() + " tidak ditemukan!"
            );
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(employeeRestService.ubahEmployee(employeeDTO.getId(), employee));
        return response;
    }

    /**
     * Nonaktivasi employee
     *
     * @param employeeDTO data transfer object untuk employee yang akan dinonaktifkan
     * @return employee yang telah dinonaktifkan
     */
    @PutMapping(value = "/nonaktif", consumes = {"application/json"})
    private BaseResponse<Employee> nonaktifEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        BaseResponse<Employee> response = new BaseResponse<>();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(employeeRestService.nonaktifkanEmployee(employeeDTO.getId()));
        return response;
    }

    /**
     * Mengambil seluruh employee
     *
     * @return daftar hasil employee
     */
    @GetMapping("/getAll")
    private BaseResponse<List<Employee>> getAllEmployee() {
        BaseResponse<List<Employee>> response = new BaseResponse<>();
        List<Employee> result = employeeRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil suatu employee
     *
     * @param idEmployee identifier employee
     * @return detail employee
     */
    @GetMapping("/{idEmployee}")
    private BaseResponse<Employee> getHasilPemeriksaan(
            @PathVariable("idEmployee") int idEmployee
    ) {
        BaseResponse<Employee> response = new BaseResponse<>();
        try {
            Employee result = employeeRestService.getById(idEmployee);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + idEmployee + " tidak ditemukan!"
            );
        }
        return response;
    }

}
