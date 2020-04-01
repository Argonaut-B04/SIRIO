package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.EmployeeDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.HasilPemeriksaanRestService;
import com.ArgonautB04.SIRIO.services.RoleRestService;
import com.ArgonautB04.SIRIO.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeRestController {

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private RoleRestService roleRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

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
        Employee employee = new Employee();

        if (employeeDTO.getUsername() != null && !employeeDTO.getUsername().equals("")) {
            if (employeeRestService.getByUsername(employeeDTO.getUsername()).isPresent())
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Username " + employeeDTO.getUsername() + " sudah ada pada database!"
                );
            employee.setUsername(employeeDTO.getUsername());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Username tidak ditemukan!"
                );
        }

        if (employeeDTO.getPassword() != null && !employeeDTO.getPassword().equals("")) {
            employee.setPassword(employeeDTO.getPassword());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Password belum diisi!"
            );
        }

        if (employeeDTO.getNama() != null && !employeeDTO.getNama().equals("")) {
            employee.setNama(employeeDTO.getNama());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nama employee belum diisi!"
            );
        }

        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().equals("")) {
            employee.setEmail(employeeDTO.getEmail());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Email employee belum diisi!"
            );
        }

        if (employeeDTO.getJabatan() != null && !employeeDTO.getJabatan().equals("")) {
            employee.setJabatan(employeeDTO.getJabatan());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Jabatan employee belum diisi!"
            );
        }

        employee.setNoHp(employeeDTO.getNoHp());

        try {
            Role role = roleRestService.getById(employeeDTO.getIdRole());
            employee.setRole(role);
        } catch (NoSuchElementException | NullPointerException e) {
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

        Employee employee;
        try {
            employee = employeeRestService.getById(employeeDTO.getId());
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + employeeDTO.getId() + " tidak ditemukan!"
            );
        }

        if (employeeDTO.getNama() != null && !employeeDTO.getNama().equals("")) {
            employee.setNama(employeeDTO.getNama());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nama employee belum diisi!"
            );
        }

        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().equals("")) {
            employee.setEmail(employeeDTO.getEmail());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Email employee belum diisi!"
            );
        }

        if (employeeDTO.getJabatan() != null && !employeeDTO.getJabatan().equals("")) {
            employee.setJabatan(employeeDTO.getJabatan());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Jabatan employee belum diisi!"
            );
        }

        employee.setNoHp(employeeDTO.getNoHp());

        try {
            Role role = roleRestService.getById(employeeDTO.getIdRole());
            employee.setRole(role);
        } catch (NoSuchElementException | NullPointerException e) {
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

        Employee employee;
        try {
            employee = employeeRestService.getById(employeeDTO.getId());
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + employeeDTO.getId() + " tidak ditemukan!"
            );
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(employeeRestService.nonaktifkanEmployee(employee.getIdEmployee()));
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
    private BaseResponse<Employee> getEmployee(
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

    /**
     * Mengambil profile employee yang login
     *
     * @return detail employee yang login
     */
    @GetMapping("/profile")
    private BaseResponse<Employee> getProfileEmployee(Principal principal) {
        BaseResponse<Employee> response = new BaseResponse<>();

        Employee result = employeeRestService.getByUsername(principal.getName()).get();

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Menghapus employee
     *
     * @param employeeDTO data transfer object untuk employee yang akan dihapus
     */
    @DeleteMapping("/hapus")
    private BaseResponse<String> hapusEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();

        Employee employee;
        try {
            employee = employeeRestService.getById(employeeDTO.getId());
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee dengan ID " + employeeDTO.getId() + " tidak ditemukan!"
            );
        }

        try {
            employeeRestService.hapusEmployee(employee.getIdEmployee());
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Employee sudah digunakan pada suatu aksi atau transaksi sehingga " +
                    "tidak dapat dihapus."
            );
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult("Employee dengan id " + employeeDTO.getId() + " terhapus!");
        return response;
    }

    @GetMapping("/login")
    public BaseResponse<Employee> authenticate(Principal principal) {
        BaseResponse<Employee> response = new BaseResponse<>();
        Employee target = employeeRestService.getByUsername(principal.getName()).get();

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(target);
        return response;
    }
}
