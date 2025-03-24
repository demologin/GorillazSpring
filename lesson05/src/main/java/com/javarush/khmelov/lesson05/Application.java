package com.javarush.khmelov.lesson05;

import com.javarush.khmelov.lesson05.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Application {
    public static void main(String[] args) {
        String basePackage = Application.class.getPackageName();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackage);
        UserService userService = applicationContext.getBean(UserService.class);
        for (long id = 1; id < 3; id++) {
            log.info("{}", userService.getUserById(id));
        }
    }
}
