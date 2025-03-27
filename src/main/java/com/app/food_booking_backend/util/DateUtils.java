package com.app.food_booking_backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}