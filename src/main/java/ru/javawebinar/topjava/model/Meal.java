package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.DateConverter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({@NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND  m.id=:id"),
               @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.user.id=:userId AND m.id = :id"),
               @NamedQuery(name = Meal.GET_ALL, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.date_Time desc"),
               @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.date_Time>=:startDate AND m.date_Time<=:endDate ORDER BY m.date_Time desc")})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = "date_Time", name = "meal_unique_dateTime_ind")})
public class Meal extends AbstractBaseEntity {

    public static final String GET = "Meal.get";
    public static final String DELETE = "Meal.delete";
    public static final String GET_ALL = "Meal.getAll";
    public static final String GET_BETWEEN = "Meal.getAllBetweenSorted";

    @Column(name = "date_Time", nullable = false, unique = true, columnDefinition = "timestamp default now()")
    @Convert(converter = DateConverter.class)
    private LocalDateTime date_Time;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "calories", nullable = false)
    @Min(1)
    private int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.date_Time = dateTime;
        this.description = description;
        this.calories = calories;
    }


    public LocalDateTime getDateTime() {
        return date_Time;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return date_Time.toLocalDate();
    }

    public LocalTime getTime() {
        return date_Time.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.date_Time = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + date_Time +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }


}


