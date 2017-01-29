package ru.javawebinar.topjava.service;

import org.junit.Test;
import ru.javawebinar.topjava.model.Meal;

import javax.validation.ConstraintViolationException;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by User on 25.01.2017.
 */
public abstract class AbstractJpaMealServiceTest extends AbstractMealServiceTest{

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}
