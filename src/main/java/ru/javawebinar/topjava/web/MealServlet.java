package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;
    ConfigurableApplicationContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = context.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.add(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000,SecurityUtil.authUserId()) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("getFiltered");
                String endTimeP = request.getParameter("endTime");
                String startDateP = request.getParameter("startDate");
                String endDateP = request.getParameter("endDate");
                String startTimeP = request.getParameter("startTime");
                LocalDate startDate = null;
                LocalDate endDate = null;
                LocalTime startTime = null;
                LocalTime endTime = null;
                try {
                    if (!startDateP.isEmpty())
                        startDate = LocalDate.parse(startDateP);
                    if (!endDateP.isEmpty())
                        endDate = LocalDate.parse(endDateP);
                    if (!startTimeP.isEmpty())
                        startTime = LocalTime.parse(startTimeP);
                    if (!endTimeP.isEmpty())
                        endTime = LocalTime.parse(endTimeP);
                    request.setAttribute("meals",
                            controller.getFilteredMealsWithExceededByUserId(startDate, endDate, startTime, endTime));
                }catch (NotFoundException e){
                    request.setAttribute("meals",
                            Collections.EMPTY_LIST);
                }
                request.setAttribute("startDate", startDateP);
                request.setAttribute("endDate", endDateP);
                request.setAttribute("startTime", startTimeP);
                request.setAttribute("endTime", endTimeP);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                List<MealWithExceed> list;
                try {
                    list = controller.getMealsWithExceedByUserID();
                }catch (NotFoundException e){
                    list = Collections.EMPTY_LIST;
                }
                request.setAttribute("meals",
                        list);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        context.close();
    }
}
