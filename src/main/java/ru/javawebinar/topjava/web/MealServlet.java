package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MemoryMealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private final String ALL_MEALS = "/meals.jsp";
    private final String ADD_EDIT = "/addEdit.jsp";
    private MemoryMealRepository mealRepository= new MemoryMealRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (Objects.isNull(action)){
            req.setAttribute("meals",mealRepository.getAllMeals());
            req.getRequestDispatcher(ALL_MEALS).forward(req, resp);
        }
        else if(action.equals("add")){
            req.setAttribute("action", "add");
            req.setAttribute("title", "Add new meal");
            req.setAttribute("pageTitle", "Please complete form for adding new meal.");
            req.getRequestDispatcher(ADD_EDIT).forward(req, resp);
        }else if(action.equals("edit")){
            req.setAttribute("action", "edit");
            req.setAttribute("title", "Edit meal");
            req.setAttribute("pageTitle", "Changing meal.");
            Integer id = Integer.parseInt(req.getParameter("id"));
            Meal meal = mealRepository.getMealByID(id);
            req.setAttribute("id", meal.getId());
            req.setAttribute("dateTime", meal.getDateTime());
            req.setAttribute("description", meal.getDescription());
            req.setAttribute("calories", meal.getCalories());
            req.getRequestDispatcher(ADD_EDIT).forward(req, resp);
        }else if(action.equals("delete")){
            Meal meal = mealRepository.getMealByID(Integer.parseInt(req.getParameter("id")));
            mealRepository.deleteMeal(meal);
            req.setAttribute("meals", mealRepository.getAllMeals());
            req.getRequestDispatcher(ALL_MEALS).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = new String(req.getParameter("description"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr.isEmpty()?"0":idStr);

        Meal meal = new Meal(dateTime, description, calories, id);

        if(action.equals("add")){
            mealRepository.addMeal(meal);
        }else if(action.equals("edit")) {
            mealRepository.updateMeal(meal);
        }

        req.setAttribute("meals", mealRepository.getAllMeals());
        req.getRequestDispatcher(ALL_MEALS).forward(req, resp);
    }
}
