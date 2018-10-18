package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal greatMeal(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Meal get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<Meal> getAllByUserId(Integer userId) throws NotFoundException {
        Collection<Meal> meals = repository.getUsersMeals();
        if (meals == null)
            throw new NotFoundException("Meal not exist");
        return meals;
    }

    @Override
    public Collection<Meal> getFilteredUsersMeals(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return repository.getUsersMeals(startDate, endDate, startTime, endTime);
    }

    @Override
    public void updateMeal(Meal meal) {
        repository.updateMeal(meal);
    }
}