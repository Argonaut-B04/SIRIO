package com.ArgonautB04.SIRIO.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Settings {

    public static LocalDate stringToLocalDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(string, formatter);
    }

    public static Date stringToDate(String string) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(string);
    }
}
