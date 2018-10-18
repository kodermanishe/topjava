package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil<T> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startDate, LocalTime endDate) {
        return lt.compareTo(startDate) >= 0
                && lt.compareTo(endDate) <= 0;
    }

    public static boolean isBetween(LocalDateTime lt, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return (startDate==null?true:lt.toLocalDate().compareTo(startDate) >= 0)
                && (endDate==null?true:lt.toLocalDate().compareTo(endDate) <= 0)
                && (startTime==null?true:lt.toLocalTime().compareTo(startTime) >= 0)
                && (endTime==null?true:lt.toLocalTime().compareTo(endTime) <= 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
