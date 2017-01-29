package ru.javawebinar.topjava.web.meal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@Controller
public class MealController {

    @Autowired
    private MealRestController mealRestController;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", mealRestController.getAll()); //добавляем в модель все блюда из базы. они передадутся в meals.jsp
        return "meals"; //отрисовываем meals.jsp со всеми юзерами
    }

    @RequestMapping(value = "/meals/add", method = RequestMethod.GET)
    //обрабатываем запрос при нажатии на ссылку "Add User"
    public String addMeal(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000); //создаем новое пустое блюдо
        model.addAttribute("meal", meal); // добавляем его в модель
        return "meal"; //отрисовываем meal.jsp с нашим пустым блюдом. Там заполняем все поля и жмем "Save", после перенаправляемся на /meals/save  .POST
    }

    @RequestMapping(value = "/meals/update{mealId}", method = RequestMethod.GET)
    //обрабатываем запрос при нажатии на ссылку "Edit User" у конкретного юзера
    public String updateMeal(@PathVariable("mealId") int mealId, Model model) { //в качестве аргументов принимаем mealId и Model
        model.addAttribute("meal", mealRestController.get(mealId)); //добавляем в модель блюдо по mealId
        return "meal"; //отрисовываем meal.jsp с нашим блюдом. Там заполняем все поля и жмем "Save", после перенаправляемся на /meals/save  .POST
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String saveMeal(HttpServletRequest request) {

        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            mealRestController.create(meal);
        } else {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            mealRestController.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "meals/delete{mealId}", method = RequestMethod.GET)
    public String deleteMeal(@PathVariable("mealId") int mealId) {
        mealRestController.delete(mealId);
        return "redirect:/meals";
    }

    @RequestMapping(value = "meals/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", mealRestController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
    }
