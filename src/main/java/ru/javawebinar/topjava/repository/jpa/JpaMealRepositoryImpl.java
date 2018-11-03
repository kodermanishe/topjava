package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(!meal.isNew() && get(meal.getId(), userId)==null){
            throw new NotFoundException("Not found");
        }
        User user = em.getReference(User.class, userId);
        meal.setUser(user);
        return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        boolean b = em.createNamedQuery(Meal.DELETE)
                .setParameter("userId", userId)
                .setParameter("id", id).executeUpdate()!=0;
        return b;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> list = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("userId", userId)
                .setParameter("id", id).getResultList();
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return  em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> list = em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
        return list;
    }
}