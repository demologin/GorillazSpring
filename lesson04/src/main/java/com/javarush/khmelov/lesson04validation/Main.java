package com.javarush.khmelov.lesson04validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Создаем валидатор
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Создаем тестовый объект
        User user = new User("as");

        // Проводим валидацию
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Выводим результаты
        if (!violations.isEmpty()) {
            System.out.println("Найдены ошибки валидации:");
            for (ConstraintViolation<User> violation : violations) {
                System.out.println(violation.getMessage());
            }
        } else {
            System.out.println("Объект валиден");
        }
    }
}