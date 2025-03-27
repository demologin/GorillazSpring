package com.javarush.khmelov.lesson06;

import com.javarush.khmelov.lesson06.model.User;
import com.javarush.khmelov.lesson06.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@ConfigurationPropertiesScan
@SpringBootApplication
@Slf4j
public class Lesson06Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Lesson06Application.class, args);
        UserService userService = applicationContext.getBean(UserService.class);
        for (long i = 1; i < 4; i++) {
            User user = userService.getUserById(i);
            log.info(user.toString());
        }
    }

}
