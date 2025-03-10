package com.javarush.khmelov.lesson01.repository;

import com.javarush.khmelov.lesson01.config.SessionCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DemoRepository {

    private final SessionCreator sessionCreator;
}
