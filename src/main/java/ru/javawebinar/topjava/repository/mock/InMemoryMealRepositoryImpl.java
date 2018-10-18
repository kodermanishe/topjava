package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.put(meal.getId(),meal);
    }

    @Override
    public void delete(int id) {
        Meal meal = getMeal(id);
        if (meal != null & meal.getUserID() != null & meal.getUserID().equals(SecurityUtil.authUserId()))
            repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        Meal meal = getMeal(id);
        if (meal != null & meal.getUserID() != null & meal.getUserID().equals(SecurityUtil.authUserId()))
            return repository.get(id);
        return null;
    }

    private Meal getMeal(Integer id){
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Collection<Meal> getUsersMeals() {
        List<Meal> mealsArr = new ArrayList<>(getAll());
        List<Meal> meals =  mealsArr.stream()
                .filter(m->m.getUserID() == null?false:m.getUserID().equals(SecurityUtil.authUserId()))
                .sorted(new Comparator<Meal>() {
                    @Override
                    public int compare(Meal o1, Meal o2) {
                        return o2.getDateTime().compareTo(o1.getDateTime());
                    }
                })
                .collect(Collectors.toList());

        return meals.size()==0?null:meals;
    }

    @Override
    public void updateMeal(Meal meal) {
        if (meal != null & meal.getUserID().equals(SecurityUtil.authUserId()))
            repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Collection<Meal> getUsersMeals(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<Meal> mealsArr = new ArrayList<>(getAll());
        List<Meal> meals =  mealsArr.stream()
                .filter(m->m.getUserID() == null?false:m.getUserID().equals(SecurityUtil.authUserId()))
                .filter( meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDate, endDate, startTime, endTime))
                .sorted(new Comparator<Meal>() {
                    @Override
                    public int compare(Meal o1, Meal o2) {
                        return o2.getDateTime().compareTo(o1.getDateTime());
                    }
                })
                .collect(Collectors.toList());

        return meals.size()==0?null:meals;
    }
}

