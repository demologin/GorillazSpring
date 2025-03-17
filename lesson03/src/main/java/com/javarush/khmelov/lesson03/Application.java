package com.javarush.khmelov.lesson03;

import com.javarush.khmelov.lesson03.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        String basePackage = Application.class.getPackageName();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackage);
        UserService userService = applicationContext.getBean(UserService.class);
        System.out.println(userService.getUserById(1L));
    }
}
