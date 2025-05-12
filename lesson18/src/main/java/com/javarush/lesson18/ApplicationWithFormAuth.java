package com.javarush.lesson18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ApplicationWithFormAuth {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationWithFormAuth.class, args);
        System.out.println();
    }
}
