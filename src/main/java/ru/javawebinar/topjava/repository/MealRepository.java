package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<MealWithExceed> getAllWithExceedFiltred(int userId, String startDate, String endDate, String startTime, String endTime);
}


