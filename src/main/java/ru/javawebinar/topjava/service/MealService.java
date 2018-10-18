package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {

    Meal greatMeal(Meal meal);

    void delete(Integer id);

    Meal get(Integer id);

    Collection<Meal> getAll();

    Collection<Meal> getAllByUserId(Integer userId);

    Collection<Meal> getFilteredUsersMeals(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    void updateMeal(Meal meal);

}