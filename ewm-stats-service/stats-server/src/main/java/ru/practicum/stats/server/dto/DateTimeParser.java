package ru.practicum.stats.server.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern(PATTERN);

    public static LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, FMT);
    }
}