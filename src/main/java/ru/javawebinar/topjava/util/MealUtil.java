package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;


public class MealUtil {

    public static Meal createNewFromTo(MealTo mealTo) {
        return new Meal(null, mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories());
    }

    public static Meal updateFromTo(MealTo mealTo, Meal meal) {
        meal.setDateTime(mealTo.getDateTime());
        meal.setDescription(mealTo.getDescription());
        meal.setCalories(mealTo.getCalories());
        return meal;
    }

}
