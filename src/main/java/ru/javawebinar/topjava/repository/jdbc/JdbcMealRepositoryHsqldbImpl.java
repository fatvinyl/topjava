package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by User on 20.01.2017.
 */
@Component
@Profile(Profiles.HSQLDB)
public class JdbcMealRepositoryHsqldbImpl extends JdbcMealRepositoryImpl<Date>{

    public JdbcMealRepositoryHsqldbImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public  Date convertDate(LocalDateTime localDateTime) {

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
