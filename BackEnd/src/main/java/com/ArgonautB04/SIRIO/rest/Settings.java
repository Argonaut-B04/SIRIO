package com.ArgonautB04.SIRIO.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Settings {

    /**
     * Mengubah String ke LocalDate sesuai format raw input html
     *
     * @param string tanggal dalam bentuk string
     * @return tanggal dalam bentuk LocalDate
     */
    public static LocalDate stringToLocalDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(string, formatter);
    }
}
