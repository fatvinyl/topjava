package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        LOG.info("save a meal " + meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete a meal, id " + id);
        if (userId != repository.get(id).getUserId()) throw new NotFoundException("userId is not correct, can't delete");
        if (repository.get(id) != null) {
            repository.remove(id);
            return true;
        } else
            return false;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get a meal, id " + id);
        Meal meal = repository.get(id);
        if (meal.getUserId() != userId) meal = null;
        return meal;
    }


    @Override
    public List<MealWithExceed> getAllWithExceedFiltred(int userId, String startDate, String endDate, String startTime, String endTime) {
        LOG.info("get all filtered meals with exceed");
        Collection<Meal> mealList = repository.values();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getWithExceeded(mealList, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return DateTimeUtil.getAllFiltredByDateTime(mealWithExceeds, startDate, endDate, startTime, endTime).stream().
                filter(meal -> meal.getUserId() == userId).
                sorted(Comparator.comparing(MealWithExceed::getDateTime).reversed()).collect(Collectors.toList());
    }
}

