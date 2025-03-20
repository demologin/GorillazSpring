package com.javarush.khmelov.lesson04spel;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringExpressionLangualeDemo {

    public static void main(String[] args) {
        String scanpath = SpringExpressionLangualeDemo.class.getPackageName();
        var context = new AnnotationConfigApplicationContext(scanpath);
        var bean = context.getBean(ExampleBean.class);
        System.out.println("--- Демонстрация SpEL Spring ---");
        bean.showExamples();
        context.close();
    }
}

@Component
class ExampleBean {
    // 1. Простые выражения с литералами
    @Value("#{100}")
    private int simpleNumber;

    @Value("#{'Привет, Spring Expression Language!'}")
    private String greeting;

    // 2. Математические операции
    @Value("#{10 + 5}")
    private int sum;

    @Value("#{20 * 3}")
    private int multiply;

    @Value("#{50 / 2}")
    private double divide;

    // 3. Логические выражения
    @Value("#{2 > 1}")
    private boolean logicalResult1;

    @Value("#{10 > 5 and 5 < 10}")
    private boolean andOperation;

    // 4. Тернарные операторы
    @Value("#{T(java.time.LocalTime).now().hour < 12 ? 'AM' : 'PM'}")
    private String timeSuffix;

    // 5. Доступ к системным свойствам и переменным окружения
    @Value("#{systemProperties['user.name']}")
    private String userName;

    @Value("#{systemEnvironment['JAVA_HOME'] ?: 'Не установлена переменная JAVA_HOME'}")
    private String javaHome;

    // 6. Работа с коллекциями
    @Value("#{{'Математика': 90, 'Физика': 85, 'Программирование': 95}}")
    private Map<String, Integer> grades;

    // 7. Работа со строками и методами
    @Value("#{'spring expression language'.toUpperCase()}")
    private String upperCaseString;

    // Для демонстрации фильтрации в коллекциях
    private final List<Integer> scores = Arrays.asList(56, 89, 73, 95, 60, 79);

    @Value("#{T(java.lang.Math).random() * 100}")
    private double randomNumber;

    // 8. Чтение свойств из application.properties
    @Value("${app.name}")
    private String appName;

    @Value("${app.version:v0.1a}")
    private String appVersion;

    @Value("${app.max-connections:10}")
    private int maxConnections;

    // 9. Комбинирование properties и SpEL
    @Value("#{${app.min-score:0} + 50}")
    private int passingScore;

    @Value("#{systemProperties['user.name'] + ' is using ' + '${app.name}'}")
    private String userAppInfo;

    // 10. Регулярные выражения
    @Value("#{'user@example.com' matches '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$'}")
    private boolean isValidEmail;

    @Value("#{T(java.util.regex.Pattern).compile('\\d{3}-\\d{2}-\\d{4}').matcher('123-45-6789').matches()}")
    private boolean isValidSSN;

    @Value("#{'Spring Framework' matches '(?i).*spring.*'}")
    private boolean containsSpringCaseInsensitive;

    public void showExamples() {
        System.out.println("Простые выражения:");
        System.out.println("simpleNumber = " + simpleNumber);
        System.out.println("greeting = " + greeting);

        System.out.println("\nМатематические операции:");
        System.out.println("sum (10 + 5) = " + sum);
        System.out.println("multiply (20 * 3) = " + multiply);
        System.out.println("divide (50 / 2) = " + divide);

        System.out.println("\nЛогические выражения:");
        System.out.println("logicalResult1 (2 > 1) = " + logicalResult1);
        System.out.println("andOperation (10 > 5 and 5 < 10) = " + andOperation);

        System.out.println("\nТернарный оператор:");
        System.out.println("timeSuffix (#{T(java.time.LocalTime).now().hour < 12 ? 'AM' : 'PM'}) = " + timeSuffix);

        System.out.println("\nСистемные свойства и переменные окружения:");
        System.out.println("userName (systemProperties['user.name']) = " + userName);
        System.out.println("javaHome (systemEnvironment['JAVA_HOME']) = " + javaHome);

        System.out.println("\nРабота с коллекциями:");
        System.out.println("grades = " + grades);
        System.out.println("оценка по Программированию = " + grades.get("Программирование"));

        System.out.println("\nРабота со строками и методами:");
        System.out.println("upperCaseString ('spring expression language'.toUpperCase()) = " + upperCaseString);
        System.out.println("randomNumber (T(java.lang.Math).random() * 100) = " + randomNumber);

        System.out.println("\nПример фильтрации в коллекциях (программно):");
        System.out.println("Все оценки: " + scores);
        System.out.println("Оценки выше " + randomNumber + "+: " +
                           scores.stream().filter(score -> score > randomNumber).toList());

        System.out.println("\nЧтение свойств из application.properties:");
        System.out.println("appName = " + appName);
        System.out.println("appVersion = " + appVersion);
        System.out.println("maxConnections (с значением по умолчанию) = " + maxConnections);

        System.out.println("\nКомбинирование properties и SpEL:");
        System.out.println("passingScore (минимальный балл + 50) = " + passingScore);
        System.out.println("userAppInfo (объединение со свойством) = " + userAppInfo);

        System.out.println("\nРегулярные выражения:");
        System.out.println("isValidEmail (проверка email) = " + isValidEmail);
        System.out.println("isValidSSN (проверка формата SSN) = " + isValidSSN);
        System.out.println("containsSpringCaseInsensitive (поиск без учета регистра) = " + containsSpringCaseInsensitive);
    }
}
