package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);

    void delete(int id);

    void updateMeal(Meal meal);

    Meal get(int id);

    Collection<Meal> getUsersMeals();

    Collection<Meal> getUsersMeals(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    Collection<Meal> getAll();
}
