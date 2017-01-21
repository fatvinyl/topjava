package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * Created by User on 20.01.2017.
 */
@Component
@Profile(Profiles.POSTGRES)
public class JdbcMealRepositoryPostgresImpl extends JdbcMealRepositoryImpl<LocalDateTime>{
    public JdbcMealRepositoryPostgresImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public LocalDateTime convertDate(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
