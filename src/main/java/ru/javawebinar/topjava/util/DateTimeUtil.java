package ru.javawebinar.topjava.util;

import org.springframework.cglib.core.Local;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isTimeBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isDateBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static List<MealWithExceed> getAllFiltredByDateTime(Collection<MealWithExceed> meals, String startDate, String endDate, String startTime, String endTime) {
        List<MealWithExceed> filtredList = new ArrayList<>();
        meals.stream().forEach(meal -> {
            if (isDateBetween(meal.getDateTime().toLocalDate(), (startDate == "" || startDate == null)? LocalDate.MIN : LocalDate.parse(startDate),
                    (endDate == "" || endDate == null)? LocalDate.MAX : LocalDate.parse(endDate)) &&
                    isTimeBetween(meal.getDateTime().toLocalTime(), (startTime == "" || startTime ==null)? LocalTime.MIN : LocalTime.parse(startTime),
                            (endTime == "" || endTime == null) ? LocalTime.MAX : LocalTime.parse(endTime))){
                filtredList.add(meal);
            }

        });
        return filtredList;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
