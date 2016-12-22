package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {

    Meal save(Meal meal);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    List<MealWithExceed> getAllWithExceedFiltred(int userId, String startDate, String endDate, String startTime, String endTime);

    void update(Meal meal);
}
