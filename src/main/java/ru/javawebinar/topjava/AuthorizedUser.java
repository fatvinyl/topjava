package ru.javawebinar.topjava;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import static java.util.Objects.requireNonNull;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser extends org.springframework.security.core.userdetails.User { //возвращаемый юзер должен имплементировать интерфейс Userdetails
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public static AuthorizedUser safeGet() { //достает безопасно авторизированного юзера
//        в SecurityContextHolder содержится информация (principal) о пользователе, работающем в наст. момент с приложением
//        Authentication представляет пользователя (Principal) с точки зрения Spring Security.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int id() {
        return get().userTo.getId();
    }

    public static int getCaloriesPerDay() {
        return get().userTo.getCaloriesPerDay();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
