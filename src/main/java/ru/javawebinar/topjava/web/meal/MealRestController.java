package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getMealsWithExceedByUserID() throws NotFoundException {
        return MealsUtil.getWithExceeded(service.getAllByUserId(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredMealsWithExceededByUserId(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        return MealsUtil.getWithExceeded(service.getFilteredUsersMeals(startDate, endDate, startTime, endTime), SecurityUtil.authUserCaloriesPerDay());
    }

    public void delete(Integer id){
        service.delete(id);
    }

    public Meal get(Integer id){
        return service.get(id);
    }

    public void add(Meal meal){
        service.greatMeal(meal);
    }

    public void updateMeal(Meal meal, Integer id){
        assureIdConsistent(meal, id);
        service.updateMeal(meal);
    }

    public Collection<Meal> getAll(){
        return service.getAll();
    }

}