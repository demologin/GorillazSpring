package com.javarush.khmelov.exp;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


public class LifeCycleApplication {
    public static void main(String[] args) {
        String packageName = LifeCycleApplication.class.getPackageName();
        var annotationConfigApplicationContext = new AnnotationConfigApplicationContext(packageName);
        try (annotationConfigApplicationContext) {
            System.out.println("======================= start app ====================");
            var bean = annotationConfigApplicationContext.getBean(RegularBean.class);
            System.out.println(bean);
            System.out.println("======================= stop app =====================");
        }
    }
}


// Этап 1: BeanFactoryPostProcessor
@Component
class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("1. CustomBeanFactoryPostProcessor - Модификация определений бинов");
        // Изменение свойств бинов перед созданием
    }

}


// Этап 2: BeanPostProcessor
@Component
class CustomBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;


    public CustomBeanPostProcessor() {
        System.out.println("2. init CustomBeanPostProcessor - обработчик beans");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("\tCustomBPP - Before: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("\tCustomBPP - After: " + beanName);
        return bean;
    }
}

@Configuration
@PropertySource("classpath:application.properties")
class Config {
    public Config() {
        System.out.println("3. init Config - special config bean");
    }
}

// Этап 3: BeanDefinitionReader
@Component
class CustomBeanDefinitionReader implements BeanDefinitionReader {

    public CustomBeanDefinitionReader() {
        System.out.println("3. init CustomBeanDefinitionReader - special bean");
    }

    public void loadBeanDefinitions() {
        System.out.println("CustomBeanDefinitionReader - Загрузка конфигурации бинов");
        // Чтение XML или Java-конфигурации
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return null;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return null;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return null;
    }

    @Override
    public BeanNameGenerator getBeanNameGenerator() {
        return null;
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        return 0;
    }
}




// Этап 4: Обычные бины с @Value или сеттерами
@Component
class DependencyBean {

    public DependencyBean() {
        System.out.println("4.1. init DependencyBean Создание независимого бина");
    }

//    private RegularBean regularBean;
//
//    @Autowired
//    public void setDependency(RegularBean regularBean) {
//        System.out.println("\t\tDependencyBean Установка зависимости через сеттер regularBean");
//        this.regularBean = regularBean;
//    }

    @PostConstruct
    public void init() {
        System.out.println("\t\tDependencyBean Инициализация независимого бина");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("\t\tDependencyBean Уничтожение независимого бина");
    }
}

@Component
class RegularBean {
    private String appName;

    @Autowired
    private DependencyBean dependencyBean;

    public RegularBean() {
        System.out.println("4.2. init RegularBean Создание обычного бина");
    }

    @Autowired
    public void setDependency(DependencyBean dependency) {
        System.out.println("\t\tRegularBean Установка зависимости через сеттер");
        this.dependencyBean = dependency;
    }

    @PostConstruct
    public void init() {
        System.out.println("\t\tRegularBean Инициализация обычного бина");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("\t\tRegularBean Уничтожение независимого бина");
    }
}


