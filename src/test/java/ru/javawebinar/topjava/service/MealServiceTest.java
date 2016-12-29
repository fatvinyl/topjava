package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_TIME_FORMATTER;

/**
 * Created by User on 29.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
    Meal meal = service.get(USER_MEAL_ID_1, USER_ID);
//    Meal meal = service.get(USER_MEAL_ID_1, ADMIN_ID);
    MATCHER.assertEquals(USER_MEAL_1, meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_MEAL_ID_1, USER_ID);
//        service.delete(USER_MEAL_ID_1, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_3, USER_MEAL_2), service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        LocalDate dateBegin = LocalDate.parse("2016-12-29");
        LocalDate dateEnd = LocalDate.parse("2016-12-30");
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_2, USER_MEAL_1), service.getBetweenDates(dateBegin, dateEnd, USER_ID));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        LocalDateTime dateTimeBegin = LocalDateTime.parse("2016-12-29 04:00", DATE_TIME_FORMATTER);
        LocalDateTime dateTimeEnd = LocalDateTime.parse("2016-12-30 04:00", DATE_TIME_FORMATTER);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_2, USER_MEAL_1), service.getBetweenDateTimes(dateTimeBegin, dateTimeEnd, USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_3, USER_MEAL_2, USER_MEAL_1), service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updateMeal = new Meal(USER_MEAL_1);
        updateMeal.setCalories(700);
        service.update(updateMeal, USER_ID);
        MATCHER.assertEquals(updateMeal, service.get(USER_MEAL_ID_1, USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.parse("2017-01-29 04:00", DATE_TIME_FORMATTER), "ОБЕД", 1501);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1), service.getAll(USER_ID));
    }

}