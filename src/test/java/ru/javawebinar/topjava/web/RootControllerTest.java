package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 10.04.2015.
 */
public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {// у нас тут цепочка. каждый рез-тат можно применить в следующей операции
        mockMvc.perform(get("/users"))//выполняем GET запрос. все методы(get, print, status...) находятся в статических импортах
                .andDo(print())        //печатаем в консоль. удобно как минимум для отладки, чтобы посмотреть что запрос делает.
                .andExpect(status().isOk()) //проверяем, что статус HttpServletResponse 200 (т.е ОК)
                .andExpect(view().name("users")) //говорим, что вьюха называется users
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp")) //ожидаем, что форвард будет в /WEB-INF/jsp/users.jsp
                .andExpect(model().attribute("users", hasSize(2))) //ожидаем, что в модели будет атрибут users с размером 2
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }
    /*
    данные тесты тестируют запросы к
    */

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
            .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ + 2)),
                                hasProperty("calories", is(MEAL1.getCalories()))
                        )
                )));
    }


}