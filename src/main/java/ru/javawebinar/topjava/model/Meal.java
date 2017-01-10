package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;
import ru.javawebinar.topjava.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({ //преимущество NamedQueries в том, что они создаются на этапе загрузки Hibernate. Они проверяются и кэшируются. И потом уже проверенные берутся из кэша. Если сделали ошибку, то приложение просто не поднимется
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId  AND m.dateTime BETWEEN  :startDate AND :endDate ORDER BY m.dateTime DESC"),
})
@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET_BETWEEN = "Meal.getBetween";

    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "date_time", nullable = false, unique = true) //уровень базы
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false) //уровень базы
    @NotEmpty //уровень модели
    private String description;

    @Column(name = "calories", nullable = false) //уровень базы
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)//если стратегию не напишем, то вытащится юзер. А нам о не нужен. Он у нас есть и нам нужно по айдишнику достать всю его еду
    @JoinColumn(name = "user_id")
    private User user; //от юзера нам нужен только его id, но ORM умеет раотать только на уровне объектов

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
