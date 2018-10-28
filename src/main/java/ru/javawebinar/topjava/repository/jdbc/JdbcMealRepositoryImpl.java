package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert insertMeal;

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }


    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number index = insertMeal.executeAndReturnKey(map);
            meal.setId((int)index);
        }
        else if (namedJdbcTemplate.update("update meals set datetime = :datetime, description = :description, calories = :calories where id = :id",map)==0)
            return null;
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        String sql = "DELETE FROM meals where id=?";
        return jdbcTemplate.update(sql, id)>0;
    }

    @Override
    public Meal get(int id, int userId) {
        String sql = "select * from meals where id = ? order by datetime DESC";
        List<Meal> meals = jdbcTemplate.query(sql, ROW_MAPPER, id);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        String sql = "select * from meals where user_id = ? order by datetime DESC";
        List<Meal> meals = jdbcTemplate.query(sql, ROW_MAPPER, userId);
        return meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> meals = jdbcTemplate.query("select * from meals where user_id = ? and datetime between ? and ? ORDER BY datetime DESC", ROW_MAPPER,userId, startDate, endDate);
        return meals;
    }
}
