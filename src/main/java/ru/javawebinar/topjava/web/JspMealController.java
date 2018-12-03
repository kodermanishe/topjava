package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    MealRestController restController;

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("meals", restController.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    public String doDelete(@RequestParam String id){
        Integer intId = Integer.parseInt(id);
        restController.delete(intId);
        return "redirect:allMeals";
    }

    @GetMapping("/createForm")
    public String doGreat(Model model){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("update")
    public String doUpdate(@PathVariable(value = "id") String id, Model model){
        Meal meal = restController.get(Integer.parseInt(id));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/filter")
    public String getFiltered(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", restController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping("/create")
    public String postAddMeal(HttpServletRequest request){
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            restController.create(meal);
        } else {
            restController.update(meal, getId(request));
        }
        return "topjava/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


}
