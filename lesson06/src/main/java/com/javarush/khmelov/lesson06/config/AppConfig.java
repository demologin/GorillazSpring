package com.javarush.khmelov.lesson06.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "com.javarush")
public class AppConfig {
    private int integer;
    private String str;
    private Long[] longs;
    private Set<String> set;
    private Map<Integer, String> map;
    private boolean onUserService;
}
