package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.utility.ExcelGenerator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

// konsepnya adalah: dari sirio-app, saat user pilih export, redirect ke sirio-api
@RestController
@RequestMapping("/export") //user perlu login lagi untuk export (anggap saja untuk menambah keamanan)

/**
 * Kelas untuk contoh export Excel file
 */
public class ExcelTestRC {

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
}
