package ru.javawebinar.topjava.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor adds the user to the model of every requests managed
 */
public class ModelInterceptor extends HandlerInterceptorAdapter { //позволяет перехватывать вэб запросы.
//В нашем случае нужен, чтобы положить юзера в каждый запрос и потом отобразить его в шапке (кнопка Admin Profile, User Profile)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty()) {
            AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
            if (authorizedUser != null) {
                modelAndView.getModelMap().addAttribute("userTo", authorizedUser.getUserTo());
            }
        }
    }
}
