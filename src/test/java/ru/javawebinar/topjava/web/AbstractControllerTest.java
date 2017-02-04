package ru.javawebinar.topjava.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.javawebinar.topjava.AllActiveProfileResolver;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import javax.annotation.PostConstruct;

/**
 * User: gkislin
 * Date: 10.08.2014
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional //конкретно в тестах это означает, что перед тестом транзакция начинается, а по окончанию - откатывается. Т.о. база остается в одном и том же состоянии. Это стандарная запись, но могут быть ошибки, т.к. у нас есть еще и другие транзакции.
@ActiveProfiles(resolver = AllActiveProfileResolver.class)
abstract public class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Autowired
    private JpaUtil jpaUtil;

    protected MockMvc mockMvc;//специальный спринговский мок для тестирования MVC (эмуляция сервлетов).

    @Autowired
    protected UserService userService;

    @Autowired
    protected MealService mealService;

    @Autowired
    private WebApplicationContext webApplicationContext; //через автовайред подтягиваем спринг контекст

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders //так строится mockMvc. Такую запись можно найти в любом туториале.
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    @Before
    public void setUp() {  //эвиктим кэш перед тестами
        userService.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
