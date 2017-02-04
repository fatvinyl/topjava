package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;


import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MEAL2;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by User on 04.02.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID)) //делаем запрос на "/rest/meals/100002"
                .andExpect(status().isOk()) //проверяем, что статус запроса ОК (200)
                .andDo(print()) //печатаем в консоль
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) //проверяем, что тип содержимого (контент) в формате JSON
                .andExpect(MATCHER.contentMatcher(MEAL1)); //сравниваем содержание BODY, что там действительно пришел MEAL1
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        List<MealWithExceed> mealWithExceeds = MealsUtil.getWithExceeded(MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(mealWithExceeds)));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }


    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null, of(2015, Month.MAY, 30, 11, 0), "New Завтрак", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        Collection<Meal> meals = mealService.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, expected, MEAL1), meals);

    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDateTime=2015-05-30T09:00:00&endDateTime=2015-05-30T14:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                 .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
                         MealsUtil.createWithExceed(MEAL2, false),
                         MealsUtil.createWithExceed(MEAL1, false)));
    }

}