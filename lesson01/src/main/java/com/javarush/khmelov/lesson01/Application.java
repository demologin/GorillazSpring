package com.javarush.khmelov.lesson01;

import com.javarush.khmelov.lesson01.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        String basePackage = "com.javarush.khmelov.lesson01";
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackage);
        DemoService demoService = applicationContext.getBean(DemoService.class);
        System.out.println(demoService);
    }
}
