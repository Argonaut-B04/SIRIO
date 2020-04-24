package com.ArgonautB04.SIRIO.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Settings {

    public static LocalDate stringToLocalDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(string, formatter);
    }

}
