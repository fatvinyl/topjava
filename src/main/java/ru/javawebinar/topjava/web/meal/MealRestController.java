package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal save(Meal meal){
        LOG.info("save meal " + meal);
       return service.save(meal);
    }

   public void delete(int id, int userId){
        LOG.info("delete meal, id " + id);
        service.delete(id, userId);
    }

    public Meal get(int id, int userId){
       LOG.info("get meal, id " + id);
        return service.get(id, userId);
    }

    public List<MealWithExceed> getAllWithExceedFiltred(int userId, String startDate, String endDate, String startTime, String endTime){
        LOG.info("get all filtered meals with exceed");
        return service.getAllWithExceedFiltred(userId, startDate, endDate, startTime, endTime);
    }

    public void update(Meal meal){
        LOG.info("update meal " + meal);
        service.update(meal);
    }
}
