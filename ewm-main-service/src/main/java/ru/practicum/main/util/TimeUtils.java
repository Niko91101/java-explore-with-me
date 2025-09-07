package ru.practicum.main.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.lang.Nullable;

public final class TimeUtils {

    private TimeUtils() {
    }

    public static LocalDateTime parseOrNull(@Nullable String value, DateTimeFormatter formatter) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(value, formatter);
    }

    public static String format(LocalDateTime ldt, DateTimeFormatter formatter) {
        return ldt == null ? null : ldt.format(formatter);
    }
}
