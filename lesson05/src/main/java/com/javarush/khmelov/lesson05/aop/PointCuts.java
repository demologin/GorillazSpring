package com.javarush.khmelov.lesson05.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class PointCuts {

    @Pointcut("within(com.javarush..*Service)")
    public void isClassService() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isAnnotatedService() {
    }

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void isAnnotatedRepository() {
    }

    @Pointcut("isAnnotatedService() || isClassService()")
    public void isService() {}

    @Pointcut("execution(public * *.*ById(*))")
    public void isMethodWithSuffixId() {
    }
}
