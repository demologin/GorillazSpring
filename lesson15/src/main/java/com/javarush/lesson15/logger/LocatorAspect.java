package com.javarush.lesson15.logger;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Aspect
@EnableAspectJAutoProxy
public class LocatorAspect {

    @Pointcut("isRepositoryInterface()||isRepositoryPackage()")
    public void isRepository() {
    }

    @Pointcut("within(com..repository.impl)")
    public void isRepositoryPackage() {
    }

    @Pointcut("this(org.springframework.data.repository.Repository) ||" +
              "this(org.springframework.data.r2dbc.repository.R2dbcRepository)")
    public void isRepositoryInterface() {
    }

    @PostConstruct
    void initialize() {
        log.info("======= Initializing LocatorAspect =======");
    }

}
