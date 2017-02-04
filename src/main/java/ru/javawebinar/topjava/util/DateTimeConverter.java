package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by User on 05.02.2017.
 */
final class DateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
