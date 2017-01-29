package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)//сюда заходим при запуске программы
    public String root() {
        return "index";
    } //возвращаем страницу index.jsp

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {  //в метод передаем модель (по сути это просто Map(K,V))
        model.addAttribute("users", service.getAll()); // добавляем в модель всех юзеров из базы.
        return "users"; //возвращаем страницу user.jsp. там будет видна наша модель
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST) //при выборе типа юзера в index.jsp и нажатии кнопки (запрос POST) попадаем сюда
    public String setUser(HttpServletRequest request) { //в метод передаем запрос
        int userId = Integer.valueOf(request.getParameter("userId")); //из запроса достаем userId
        AuthorizedUser.setId(userId);//авторизируем пользователя по userId
        return "redirect:meals"; //редиректимся на запрос /meals с методом GET
    }
}
