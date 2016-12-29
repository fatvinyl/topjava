package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Objects;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_TIME_FORMATTER;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public final static int USER_MEAL_ID_1 = START_SEQ + 2;
    public final static int USER_MEAL_ID_2 = START_SEQ + 3;
    public final static int USER_MEAL_ID_3 = START_SEQ + 4;
    public final static int ADMIN_MEAL_ID_1 = START_SEQ + 5;
    public final static int ADMIN_MEAL_ID_2 = START_SEQ + 6;
    public final static int ADMIN_MEAL_ID_3 = START_SEQ + 7;

    public final static Meal USER_MEAL_1 = new Meal(USER_MEAL_ID_1, LocalDateTime.parse("2016-12-29 04:00", DATE_TIME_FORMATTER), "завтрак", 350);
    public final static Meal USER_MEAL_2 = new Meal(USER_MEAL_ID_2, LocalDateTime.parse("2016-12-30 04:00", DATE_TIME_FORMATTER), "обед", 550);
    public final static Meal USER_MEAL_3 = new Meal(USER_MEAL_ID_3, LocalDateTime.parse("2016-12-31 04:00", DATE_TIME_FORMATTER), "ужин", 550);
    public final static Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1, LocalDateTime.parse("2016-12-29 04:00", DATE_TIME_FORMATTER), "завтрак", 400);
    public final static Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID_2, LocalDateTime.parse("2016-12-30 04:00", DATE_TIME_FORMATTER), "обед", 500);
    public final static Meal ADMIN_MEAL_3 = new Meal(ADMIN_MEAL_ID_3, LocalDateTime.parse("2016-12-31 04:00", DATE_TIME_FORMATTER), "ужин", 600);


    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.toString(), actual.toString())
                    )
    );

}
