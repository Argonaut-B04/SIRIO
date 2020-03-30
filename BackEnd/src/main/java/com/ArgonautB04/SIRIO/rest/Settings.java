package com.ArgonautB04.SIRIO.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static Date stringToDate(String string) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(string);
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.of("Asia/Jakarta"))
                .toLocalDate();
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.of("Asia/Jakarta"))
                .toInstant());
    }
}
