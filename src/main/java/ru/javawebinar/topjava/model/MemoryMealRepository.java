package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.AutoIncrement;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class MemoryMealRepository {
    private static AutoIncrement counter = AutoIncrement.getInstance();
    private static Map<Integer, Meal> meals = new HashMap<>();
    static {
        Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Каша", 500, counter.getID()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, counter.getID()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, counter.getID()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, counter.getID()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, counter.getID()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, counter.getID())
        ).forEach(m -> meals.put(m.getId(), m));
    }

    public MemoryMealRepository() {
    }

    public List<MealWithExceed> getAllMeals(){
        List<Meal> allMeals = new ArrayList<>(meals.values());
        return MealsUtil.getFilteredWithExceeded(allMeals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }

    public  void addMeal(Meal meal){
        meal.setId(counter.getID());
        meals.put(meal.getId(), meal);
    }

    public void deleteMeal(Meal meal){
        meals.remove(meal.getId());
    }

    public void updateMeal(Meal meal){
        meals.put(meal.getId(), meal);
    }

    public Meal getMealByID(Integer id){
        return meals.get(id);
    }
}