package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    AtomicInteger lastId = new AtomicInteger(0);


    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if (repository.get(id) != null) {
            repository.remove(id);
            return true;
        } else return false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(lastId.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        if (repository.get(id) != null) {
            return repository.get(id);
        } else return null;

    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> users= new ArrayList<>(repository.values());
        users.sort(Comparator.comparing(user -> user.getName()));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        User user = null;
        for (Map.Entry<Integer, User> userEntry : repository.entrySet()) {
            if (email.equals(userEntry.getValue().getEmail())) user = userEntry.getValue();
        }
        return user;
    }
}
