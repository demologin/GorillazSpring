package com.javarush.khmelov.lesson01.service;

import com.javarush.khmelov.lesson01.repository.DemoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DemoService {

    private final DemoRepository demoRepository;


    @Override
    public String toString() {
        return "DemoService{" +
               "demoRepository=" + demoRepository +
               '}';
    }
}
