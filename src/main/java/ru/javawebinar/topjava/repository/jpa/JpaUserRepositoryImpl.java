package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: gkislin
 * Date: 29.08.2014
 */
@Repository //тот же компонент, только еще преобразовывает эксэпшны
@Transactional(readOnly = true) //транзакция Spring. readOnly позволяет оптимизировать запросы к базе, не делать флаши и т.д. Если транзакцию убрать то оптимизация не проводится.
                                //будет действовать на все методы, где транзакция явно не указана
public class JpaUserRepositoryImpl implements UserRepository {

/*
    запись, если бы работали на Hibernate без JPA
    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }
*/

    @PersistenceContext
    private EntityManager em; //аналог сессии Spring, но сделан по спецификации JPA. Когда мы хотим сделать запрос к базе, из EntityManagerFactory создается ЕМ,
                              //все, что мы достаем из базы - попадает в PersistanceContext, если делаем запрос второй раз, то все уже берется из контекста. Это как кэш м/у кодом и базой, куда клаутся Entity
    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user); //persist - это как save
            return user;
        } else {
            return em.merge(user);//merge - это как update
        }
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {

/*      User ref = em.getReference(User.class, id);  //getReference достает ссылку на объект. если в контексте это есть, то он достает весь объект. если нет, то он создает объект с id, а остальные поля LAZY. при первом обращени он идет в базу и достает объект
        em.remove(ref); //для удаления ORMу нужен объект, а мы, чтобы не идти за ним в базу создаем getReference с id.

        Query<User> query = em.createQuery("DELETE FROM User u WHERE u.id=:id");//запросы на языке HQL/JPQL, где все операции мы производим с объектами
        return query.setParameter("id", id).executeUpdate() != 0;
*/
        return em.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class).setParameter(1, email).getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
