package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by User on 19.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATA_JPA})
public class MealServiceDataJpaTest extends MealServiceTest{
}
