package com.ArgonautB04.SIRIO.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Kelas untuk export simple Excel File
 */
public class ExcelGenerator {

    /**
     * Insert langsung dari array ke excel.
     *
     * @param sheetName nama sheet
     * @param columns nama kolom yang akan ditulis di paling atas (header)
     * @param contents konten dari setiap cell
     * @return ByteArrayInputStream, file dalam bentuk byte
     * @throws Exception formalitas
     */
    public ByteArrayInputStream exportExcel(String sheetName, String[] columns, String[][] contents) throws Exception {

        // buat workbook dan papan tulis nya
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // buat sheet dan style untuk columns
        Sheet sheet = workbook.createSheet(sheetName);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // buat row pertama
        Row headerRow = sheet.createRow(0);

        // tulis header di row pertama
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // mulai dari row kedua, tulis seluruh konten
        int rowIdx = 1;
        for (String[] content : contents) {
            Row row = sheet.createRow(rowIdx);

            for (int i = 0; i < content.length; i++) {
                row.createCell(i).setCellValue(content[i]);
            }
            rowIdx++;
        }

        // cetak ke workbook dan tutup workbook
        workbook.write(out);
        workbook.close();

        // return hasil cetak di papan tulis
        return new ByteArrayInputStream(out.toByteArray());
    }
}
