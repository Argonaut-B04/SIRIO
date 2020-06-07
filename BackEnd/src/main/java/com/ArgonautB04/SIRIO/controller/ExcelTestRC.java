package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.utility.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;

// konsepnya adalah: dari sirio-app, saat user pilih export, redirect ke sirio-api
@RestController
@RequestMapping("/api/v1/export") //user perlu login lagi untuk export (anggap saja untuk menambah keamanan)

/**
 * Kelas untuk contoh export Excel file
 */
public class ExcelTestRC {

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Contoh Controller untuk export Excel File.
     *
     * @param id identifier diatur supaya tidak ada user yang akses halaman yang sama
     * @return Hasil export file Excel
     * @throws Exception Random Export, ikut contoh
     */
    @GetMapping("/testing/{id}") // URL nya
    public ResponseEntity<InputStreamSource> exp(
            @PathVariable(name = "id") String id //ini identifier, silahkan ubah (perlu login lagi)
    ) throws Exception {

        // disini bisa implementasi pembatasan hak akses

        // Excel Generator, silahkan di cek di utility/ExcelGenerator (buatan manual)
        ExcelGenerator exc = new ExcelGenerator();

        // nama sheet
        String sheetname = "UwU Sheet";

        // Nama kolom
        String[] column = {"nomor", "nama depan", "nama belakang"};

        // isi dari kolom
        String[][] content = {
                {"1", "Kurumi", "Tokisaki"},
                {"2", "Sebastian", "Michaelis"},
                {"3", "", "Kazuma"}, // kosongkan cell ke 2 dengan masukan string kosong
                {}, // kosongkan 1 baris dengan masukan {}
                {"4", "Megurine", "Luka", "Nightfever"} // jumlah cell pada 1 row harus sama dengan kolom, kalau tidak, dia offside
        };

        // oper sebagai ByteArrayInputStream
        ByteArrayInputStream in = exc.exportExcel(sheetname, column, content);

        // pasang HTTPHeaders, ini wajib (supaya browser terima kontennya ini adalah file Excel)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tester.xlsx"); // Pasang nama file yang sesuai

        // Kirim responseentity
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    /**
     * Contoh Controller untuk export Excel File.
     *
     * @return Hasil export file Excel
     * @throws Exception Random Export, ikut contoh
     */
    @GetMapping("/employee") // URL nya
    public ResponseEntity<InputStreamSource> exp(Principal principal) throws Exception {

        // disini bisa implementasi pembatasan hak akses
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        if (employee.getIdEmployee() != 1) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Employee dengan ID " + employee.getIdEmployee() +
                    " tidak dapat mendownload data Employee!"
            );
        }

        // Excel Generator, silahkan di cek di utility/ExcelGenerator (buatan manual)
        ExcelGenerator exc = new ExcelGenerator();

        // nama sheet
        String sheetname = "Employee Data";

        // Nama kolom
        String[] column = {"id", "nama", "status", "username", "no_hp", "email", "jabatan", "role"};

        // isi dari kolom
        String[][] yes = {
                {"1", "Kurumi", "Tokisaki"},
                {"2", "Sebastian", "Michaelis"},
                {"3", "", "Kazuma"}, // kosongkan cell ke 2 dengan masukan string kosong
                {}, // kosongkan 1 baris dengan masukan {}
                {"4", "Megurine", "Luka", "Nightfever"} // jumlah cell pada 1 row harus sama dengan kolom, kalau tidak, dia offside
        };

        List<Employee> daftarEmployee = employeeRestService.getAllWithNonAktif();
        String[][] content = new String[daftarEmployee.size()][8];
        for (int i = 0; i < daftarEmployee.size(); i++) {
            content[i][0] = daftarEmployee.get(i).getIdEmployee().toString();
            content[i][1] = daftarEmployee.get(i).getNama();
            content[i][2] = daftarEmployee.get(i).getStatus().toString();
            content[i][3] = daftarEmployee.get(i).getUsername();
            content[i][4] = daftarEmployee.get(i).getNoHp() != null ? daftarEmployee.get(i).getNoHp(): "";
            content[i][5] = daftarEmployee.get(i).getEmail();
            content[i][6] = daftarEmployee.get(i).getJabatan();
            content[i][7] = daftarEmployee.get(i).getRole().getNamaRole();
        }

        // oper sebagai ByteArrayInputStream
        ByteArrayInputStream in = exc.exportExcel(sheetname, column, content);

        // pasang HTTPHeaders, ini wajib (supaya browser terima kontennya ini adalah file Excel)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employee.xlsx"); // Pasang nama file yang sesuai

        // Kirim responseentity
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
}
