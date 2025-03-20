package com.javarush.khmelov.lesson04;

import com.javarush.khmelov.lesson04.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        String basePackage = Application.class.getPackageName();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackage);
        UserService userService = applicationContext.getBean(UserService.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(userService.getUserById(1L));
        }
    }
}
