package ru.practicum.main.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateTimeConfig {

    @Bean
    public DateTimeFormatter apiDateTimeFormatter(AppProperties props) {
        return DateTimeFormatter.ofPattern(props.getTimeFormat());
    }

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer(DateTimeFormatter apiDateTimeFormatter) {
        return new LocalDateTimeSerializer(apiDateTimeFormatter);
    }
}
