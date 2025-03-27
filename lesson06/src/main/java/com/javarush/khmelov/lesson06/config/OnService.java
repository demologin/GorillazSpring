package com.javarush.khmelov.lesson06.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnService implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String needService = context.getEnvironment().getProperty("com.javarush.on-user-service");
        return "true".equalsIgnoreCase(needService);
    }
}
