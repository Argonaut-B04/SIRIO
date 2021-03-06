package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.Role;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.rest.EmployeeDTO;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.HasilPemeriksaanRestService;
import com.argonautb04.sirio.services.RoleRestService;
import com.argonautb04.sirio.services.TugasPemeriksaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*")
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
    private BaseResponse<Employee> tambahEmployee(@RequestBody EmployeeDTO employeeDTO) {
        BaseResponse<Employee> response = new BaseResponse<>();
        Employee employee = new Employee();

        if (employeeDTO.getUsername() != null && !employeeDTO.getUsername().equals("")) {
            if (employeeRestService.getByUsername(employeeDTO.getUsername()).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Username " + employeeDTO.getUsername() + " sudah ada pada database!");
            employee.setUsername(employeeDTO.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username tidak ditemukan!");
        }

        if (employeeDTO.getPassword() != null && !employeeDTO.getPassword().equals("")) {
            employee.setPassword(employeeDTO.getPassword());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password belum diisi!");
        }

        if (employeeDTO.getNama() != null && !employeeDTO.getNama().equals("")) {
            employee.setNama(employeeDTO.getNama());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nama employee belum diisi!");
        }

        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().equals("")) {
            if (employeeRestService.getByEmail(employeeDTO.getEmail()).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Email " + employeeDTO.getEmail() + " sudah ada pada database!");
            employee.setEmail(employeeDTO.getEmail());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email employee belum diisi!");
        }

        if (employeeDTO.getJabatan() != null && !employeeDTO.getJabatan().equals("")) {
            employee.setJabatan(employeeDTO.getJabatan());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jabatan employee belum diisi!");
        }

        employee.setNoHp(employeeDTO.getNoHp());

        try {
            Role role = roleRestService.getById(employeeDTO.getIdRole());
            employee.setRole(role);
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Role dengan ID " + employeeDTO.getIdRole() + " tidak ditemukan!");
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
    @PostMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<Employee> ubahEmployee(@RequestBody EmployeeDTO employeeDTO) {
        BaseResponse<Employee> response = new BaseResponse<>();

        Employee employee;
        try {
            employee = employeeRestService.getById(employeeDTO.getId());
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee dengan ID " + employeeDTO.getId() + " tidak ditemukan!");
        }

        if (employee.getStatus() == Employee.Status.NONAKTIF)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Employee dengan ID " + employeeDTO.getId() + " sudah tidak aktif!");

        if (employeeDTO.getNama() != null && !employeeDTO.getNama().equals("")) {
            employee.setNama(employeeDTO.getNama());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nama employee belum diisi!");
        }

        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().equals("")) {
            if (employeeRestService.getByEmail(employeeDTO.getEmail()).isPresent()
                    && !employeeDTO.getEmail().equals(employee.getEmail()))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Email " + employeeDTO.getEmail() + " sudah ada pada database!");
            employee.setEmail(employeeDTO.getEmail());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email employee belum diisi!");
        }

        if (employeeDTO.getJabatan() != null && !employeeDTO.getJabatan().equals("")) {
            employee.setJabatan(employeeDTO.getJabatan());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jabatan employee belum diisi!");
        }

        employee.setNoHp(employeeDTO.getNoHp());

        try {
            Role role = roleRestService.getById(employeeDTO.getIdRole());
            employee.setRole(role);
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Role dengan ID " + employeeDTO.getIdRole() + " tidak ditemukan!");
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(employeeRestService.ubahEmployee(employeeDTO.getId(), employee));
        return response;
    }

    // /**
    // * Nonaktivasi employee
    // *
    // * @param employeeDTO data transfer object untuk employee yang akan
    // dinonaktifkan
    // * @return employee yang telah dinonaktifkan
    // */
    // @PutMapping(value = "/nonaktif", consumes = {"application/json"})
    // private BaseResponse<Employee> nonaktifEmployee(
    // @RequestBody EmployeeDTO employeeDTO
    // ) {
    // BaseResponse<Employee> response = new BaseResponse<>();
    //
    // Employee employee;
    // try {
    // employee = employeeRestService.getById(employeeDTO.getId());
    // } catch (NoSuchElementException | NullPointerException e) {
    // throw new ResponseStatusException(
    // HttpStatus.NOT_FOUND, "Employee dengan ID " + employeeDTO.getId() + " tidak
    // ditemukan!"
    // );
    // }
    //
    // response.setStatus(200);
    // response.setMessage("success");
    // response.setResult(employeeRestService.nonaktifkanEmployee(employee.getIdEmployee()));
    // return response;
    // }

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
     * Mengambil seluruh branch manager
     *
     * @return daftar hasil employee
     */
    @GetMapping("/getAllBranchManager")
    private BaseResponse<List<Employee>> getAllBM() {
        BaseResponse<List<Employee>> response = new BaseResponse<>();
        ArrayList<Employee> result = new ArrayList<>();
        List<Employee> emp = employeeRestService.getAll();
        for (Employee e : emp) {
            if (e.getRole().getIdRole() == 6) {
                result.add(e);
            }
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh branch manager yang belum memiliki kantor cabang
     *
     * @return daftar hasil employee
     */
    @GetMapping("/getUnassignedBM")
    private BaseResponse<List<Employee>> getUnassignedBM() {
        BaseResponse<List<Employee>> response = new BaseResponse<>();
        ArrayList<Employee> result = new ArrayList<>();
        List<Employee> emp = employeeRestService.getUnassignedBM();
        for (Employee e : emp) {
            if (e.getRole().getIdRole() == 6) {
                result.add(e);
            }
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh QA Officer
     *
     * @return daftar hasil employee
     */
    @GetMapping("/getAllQAOfficer")
    private BaseResponse<List<Employee>> getAllQAOfficer() {
        BaseResponse<List<Employee>> response = new BaseResponse<>();
        ArrayList<Employee> result = new ArrayList<>();
        Employee allEmp = new Employee();
        List<Employee> emp = employeeRestService.getAll();
        for (Employee e : emp) {
            if (e.getRole().getIdRole() == 5 || e.getRole().getIdRole() == 7) {
                result.add(e);
            }
        }
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

    /**
     * Mengambil seluruh QA Officer untuk dropdown
     *
     * @return daftar hasil employee
     */
    @GetMapping("/getAllQAOfficerDD")
    private BaseResponse<List<Employee>> getAllQAOfficerDD() {
        BaseResponse<List<Employee>> response = new BaseResponse<>();
        ArrayList<Employee> result = new ArrayList<>();
        Employee allEmp = new Employee();
        allEmp.setIdEmployee(0);
        allEmp.setNama("Semua QA Officer");
        result.add(allEmp);
        List<Employee> emp = employeeRestService.getAll();
        for (Employee e : emp) {
            if (e.getRole().getIdRole() == 5 || e.getRole().getIdRole() == 7) {
                result.add(e);
            }
        }
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
    private BaseResponse<Employee> getEmployee(@PathVariable("idEmployee") int idEmployee) {
        BaseResponse<Employee> response = new BaseResponse<>();
        try {
            Employee result = employeeRestService.getById(idEmployee);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee dengan ID " + idEmployee + " tidak ditemukan!");
        }
        return response;
    }

    /**
     * Mengecek apakah username employee sudah terdapat di sistem
     *
     * @param username identifier employee
     * @return Boolean checker
     */
    @GetMapping("/check/username/{username}")
    private BaseResponse<Boolean> checkUsernameEmployee(@PathVariable("username") String username) {
        Optional<Employee> employeeOptional = employeeRestService.getByUsername(username);
        return new BaseResponse<>(200, "success", employeeOptional.isPresent());
    }

    /**
     * Mengecek apakah email employee sudah terdapat di sistem
     *
     * @return Boolean checker
     */
    @PostMapping("/check/email")
    private BaseResponse<Boolean> checkEmailEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOptional = employeeRestService.getByEmail(employeeDTO.getEmail());
        return new BaseResponse<>(200, "success", employeeOptional.isPresent());
    }

    /**
     * Mengambil profile employee yang login
     *
     * @return detail employee yang login
     */
    @GetMapping("/profile")
    private BaseResponse<Employee> getProfileEmployee(Principal principal) {
        BaseResponse<Employee> response = new BaseResponse<>();
        Employee result = employeeRestService.validateEmployeeExistByPrincipal(principal);
        return new BaseResponse<>(200, "success", result);
    }

    /**
     * Menghapus employee
     *
     * @param employeeDTO data transfer object untuk employee yang akan dihapus
     */
    @PostMapping("/hapus")
    private BaseResponse<String> hapusEmployee(@RequestBody EmployeeDTO employeeDTO) {
        BaseResponse<String> response = new BaseResponse<>();

        Employee employee;
        try {
            employee = employeeRestService.getById(employeeDTO.getId());
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee dengan ID " + employeeDTO.getId() + " tidak ditemukan!");
        }

        response.setStatus(200);
        response.setMessage("success");

        try {
            employeeRestService.hapusEmployee(employee.getIdEmployee());
        } catch (DataIntegrityViolationException e) {
            employeeRestService.nonaktifkanEmployee(employee.getIdEmployee());
            response.setResult("Employee dengan id " + employeeDTO.getId() + " dinonaktifkan!");
            return response;
        }

        response.setResult("Employee dengan id " + employeeDTO.getId() + " terhapus!");
        return response;
    }

    /**
     * Mengubah password employee
     *
     * @param employeeDTO data transfer object untuk employee yang akan diubah
     *                    password nya
     */
    @PostMapping("/ubahPassword")
    private BaseResponse<Boolean> ubahPasswordEmployee(@RequestBody EmployeeDTO employeeDTO, Principal principal) {
        Employee targetEmployee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        Boolean changeSuccess = employeeRestService.changePassword(targetEmployee.getUsername(),
                employeeDTO.getPassword(), employeeDTO.getNewPassword());

        return new BaseResponse<>(200, "success", changeSuccess);
    }

    @GetMapping("/login")
    public BaseResponse<Employee> authenticate(Principal principal) {
        Employee target = employeeRestService.validateEmployeeExistByPrincipal(principal);
        return new BaseResponse<>(200, "success", target);
    }
}
