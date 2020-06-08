package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.repository.EmployeeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EmployeeRestServiceImpl implements EmployeeRestService {

    @Autowired
    private EmployeeDB employeeDb;

    @Override
    public Employee buatEmployee(Employee employee) {
        employee.setPassword(encrypt(employee.getPassword()));
        employee.setStatus(Employee.Status.AKTIF);
        return employeeDb.save(employee);
    }

    private String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public Employee ubahEmployee(int idEmployee, Employee employee) {
        Employee target = getById(idEmployee);
        target.setEmail(employee.getEmail());
        target.setNama(employee.getNama());
        target.setNoHp(employee.getNoHp());
        // password harus di encrypt
        // target.setPassword(employee.getPassword());
        target.setRole(employee.getRole());
        target.setUsername(employee.getUsername());
        target.setJabatan(employee.getJabatan());
        return employeeDb.save(target);
    }

    @Override
    public Employee getById(int idEmployee) {
        Optional<Employee> employee = employeeDb.findByIdEmployeeAndStatus(idEmployee, Employee.Status.AKTIF);
        if (employee.isPresent())
            return employee.get();
        else
            throw new NoSuchElementException();
    }

    @Override
    public Optional<Employee> getByUsername(String username) {
        return employeeDb.findByUsername(username);
    }

    @Override
    public Optional<Employee> getByEmail(String email) {
        return employeeDb.findByEmail(email);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDb.findAllByStatus(Employee.Status.AKTIF);
    }

    @Override
    public List<Employee> getAllWithNonAktif() {
        return employeeDb.findAll();
    }

    @Override
    public Employee nonaktifkanEmployee(int idEmployee) {
        Employee employee = getById(idEmployee);
        employee.setStatus(Employee.Status.NONAKTIF);
        return employee;
    }

    @Override
    public Employee aktifkanEmployee(int idEmployee) {
        Employee employee = getById(idEmployee);
        employee.setStatus(Employee.Status.AKTIF);
        return employee;
    }

    @Override
    public void hapusEmployee(int idEmployee) throws DataIntegrityViolationException {
        employeeDb.deleteById(idEmployee);
    }

    @Override
    public Employee validateEmployeeExistByPrincipal(Principal principal) {
        return validateEmployeeExistByUsername(principal.getName());
    }

    @Override
    public Employee validateEmployeeExistByUsername(String username) {
        Optional<Employee> employeeOptional = getByUsername(username);
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio");
        } else {
            return employeeOptional.get();
        }
    }

    @Override
    public Employee validateEmployeeExistById(Integer id) {
        Optional<Employee> target = employeeDb.findByIdEmployeeAndStatus(id, Employee.Status.AKTIF);
        if (target.isPresent()) {
            return target.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Tidak ditemukan employee aktif dengan id " + id);
        }
    }

    @Override
    public void validateRolePermission(Employee employee, String requestedPermissions) {
        switch (requestedPermissions) {
            case "tenggat waktu":
                if (!employee.getRole().getAccessPermissions().getAturTenggatWaktu()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke pengaturan tenggat waktu");
                }
                break;
            case "tabel rekomendasi":
                if (!employee.getRole().getAccessPermissions().getAksesTabelRekomendasi()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke daftar rekomendasi");
                }
                break;
            case "akses risk rating":
                if (!employee.getRole().getAccessPermissions().getAksesRiskRating()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke daftar risk rating");
                }
                break;
            case "ubah risk rating":
                if (!employee.getRole().getAccessPermissions().getUbahRiskRating()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk melakukan perubahan daftar risk rating");
                }
                break;
            case "akses risk level":
                if (!employee.getRole().getAccessPermissions().getAksesRiskRating()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke daftar risk level");
                }
                break;
            case "ubah risk level":
                if (!employee.getRole().getAccessPermissions().getUbahRiskLevel()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk melakukan perubahan daftar risk level");
                }
                break;
            case "tambah risiko":
                if (!employee.getRole().getAccessPermissions().getAksesTambahRisiko()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk melakukan penambahan risiko");
                }
                break;
            case "akses risiko":
                if (!employee.getRole().getAccessPermissions().getAksesRisiko()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke daftar risiko");
                }
                break;
            case "hapus risiko":
                if (!employee.getRole().getAccessPermissions().getAksesHapusRisiko()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk menghapus risiko");
                }
                break;
            case "ubah risiko":
                if (!employee.getRole().getAccessPermissions().getAksesUbahRisiko()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk mengubah risiko");
                }
                break;
            case "tabel risiko":
                if (!employee.getRole().getAccessPermissions().getAksesTabelRisiko()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke tabel risiko");
                }
                break;
            case "bukti pelaksanaan":
                if (!employee.getRole().getAccessPermissions().getAksesBuktiPelaksanaan()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses ke bukti pelaksanaan");
                }
                break;
            case "tambah bukti pelaksanaan":
                if (!employee.getRole().getAccessPermissions().getAksesTambahBuktiPelaksanaan()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk menambah bukti pelaksanaan");
                }
                break;
            case "ubah bukti pelaksanaan":
                if (!employee.getRole().getAccessPermissions().getAksesUbahBuktiPelaksanaan()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk mengubah bukti pelaksanaan");
                }
                break;
            case "persetujuan bukti pelaksanaan":
                if (!employee.getRole().getAccessPermissions().getAksesPersetujuanBuktiPelaksanaan()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses persetujuan bukti");
                }
                break;
            case "atur hierarki risiko":
                if (!employee.getRole().getAccessPermissions().getAksesUbahHierarki()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Akun anda tidak memiliki akses untuk mengatur hierarki risiko");
                }
                break;
        }
    }

    @Override
    public void simpanPerubahan(Employee employee) {
        Optional<Employee> target = employeeDb.findById(employee.getIdEmployee());
        if (target.isPresent()) {
            Employee fokus = target.get();
            fokus.setReminderTemplatePilihan(employee.getReminderTemplatePilihan());
            employeeDb.save(employee);
        }
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Employee target = validateEmployeeExistByUsername(username);

        BCryptPasswordEncoder passwordMatcher = new BCryptPasswordEncoder();
        if (passwordMatcher.matches(oldPassword, target.getPassword())) {
            target.setPassword(
                    encrypt(newPassword)
            );
            employeeDb.save(target);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setPassword(String username, String password) {
        Employee target = validateEmployeeExistByUsername(username);
        target.setPassword(
                encrypt(password)
        );
        employeeDb.save(target);
    }

    @Override
    public List<Employee> getUnassignedBM() {
        return employeeDb.findBM();
    }
}
