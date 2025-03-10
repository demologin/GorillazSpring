package com.javarush.khmelov.lesson01;

import com.javarush.khmelov.lesson01.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("beans.xml");
        DemoService demoService = applicationContext.getBean(DemoService.class);
        System.out.println(demoService);
    }
}
