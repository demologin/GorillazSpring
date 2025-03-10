package com.javarush.khmelov.lesson01.config;

import com.javarush.khmelov.lesson01.repository.DemoRepository;
import com.javarush.khmelov.lesson01.service.DemoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfig {


    @Bean
    public SessionCreator sessionCreator(ApplicationProperties properties){
        return new SessionCreator(properties);
    }

    @Bean
    public DemoRepository demoRepository(SessionCreator sessionCreator){
        return new DemoRepository(sessionCreator);
    }

    @Bean
    public DemoService demoService(DemoRepository demoRepository){
        return new DemoService(demoRepository);
    }


}
