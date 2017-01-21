package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Override
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId ")
    int delete(@Param("id") int id, @Param("userId")int userId);

    Meal findByIdAndUserId(Integer id, Integer userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(Integer userId);

    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("userId") int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=?1 AND m.user.id=?2 ORDER BY m.dateTime DESC ")
    Meal getWithUser(Integer id, Integer userId);
}
