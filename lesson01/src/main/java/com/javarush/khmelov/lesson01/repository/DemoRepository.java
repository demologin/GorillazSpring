package com.javarush.khmelov.lesson01.repository;

import com.javarush.khmelov.lesson01.config.SessionCreator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
public class DemoRepository {

    private final SessionCreator sessionCreator;
}
