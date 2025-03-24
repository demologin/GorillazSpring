package com.javarush.khmelov.lesson05.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
public class SimpleLog {

    @After("within(com..*sitory)")
    public void logAfter(JoinPoint joinPoint) {
        log.info("=".repeat(100));
    }
}
