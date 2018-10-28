package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepositoryImpl;

@ContextConfiguration({"classpath:/spring-app-test.xml",
        "classpath:/spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:/db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    InMemoryMealRepositoryImpl repository;

    @Test
    public void get() {
        //assertMatch(repository.get(START_SEQ, SecurityUtil.authUserId()), MEAL);
    }

//   @Test(expected = NotFoundException.class)
 //   public void delete() {
        //repository.delete(555, USER_ID);
  //  }

   // @Test(expected = NotFoundException.class)
   // public void update() {
       // repository.save(new Meal(null, LocalDateTime.now(), "test food", 1100), 1121);
 //   }
}