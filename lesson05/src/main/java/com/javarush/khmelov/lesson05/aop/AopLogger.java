package com.javarush.khmelov.lesson05.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy //todo move to cfg
@Slf4j
public class AopLogger {

    @Before("PointCuts.isService()")
    public void logBefore(JoinPoint joinPoint) {
        log.warn("Before method {} invoked with {}",joinPoint.getSignature(),joinPoint.getArgs());
    }

    @After("PointCuts.isAnnotatedService()")
    public void logAfter(JoinPoint joinPoint) {
        log.warn("After method {} invoked with {}",joinPoint.getSignature(),joinPoint.getArgs());
    }

    @Around(value = "PointCuts.isMethodWithSuffixId() && args(id) && this(obj)", argNames = "proceedingJoinPoint,id,obj")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint, Object id, Object obj) throws Throwable {
        try {
            log.info("around before obj={}", obj);
            Object returnValue = proceedingJoinPoint.proceed();
            log.info("around afterReturning");
            return returnValue;
        } catch (Throwable e){
            log.info("around afterThrowing");
            throw e;
        }
        finally {
            log.info("around after id={}",id);
        }
    }
}
