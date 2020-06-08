package com.argonautb04.sirio.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Settings {

    public static LocalDate stringToLocalDate(final String string) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(string, formatter);
    }

}
