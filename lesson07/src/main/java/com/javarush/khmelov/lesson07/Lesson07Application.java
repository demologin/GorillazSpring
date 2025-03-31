package com.javarush.khmelov.lesson07;

import com.javarush.khmelov.lesson07.model.User;
import com.javarush.khmelov.lesson07.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@ConfigurationPropertiesScan
@SpringBootApplication
@Slf4j
public class Lesson07Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Lesson07Application.class, args);
        UserService userService = applicationContext.getBean(UserService.class);
        for (long i = 1; i < 4; i++) {
            User user = userService.getUserById(i);
            log.info(user.toString());
        }
        List<User> users = userService.findUser("B", "E");
        for (User user : users) {
            log.warn(user.toString());
        }

        int pageSize = 4;
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.ASC, "login"));
        Page<User> userPage;
        do {
            userPage = userService.findAll(pageable);
            userPage.forEach(System.out::println);
            pageable=pageable.next();
            System.out.println(pageable);
        } while (userPage.hasNext());
    }

}
