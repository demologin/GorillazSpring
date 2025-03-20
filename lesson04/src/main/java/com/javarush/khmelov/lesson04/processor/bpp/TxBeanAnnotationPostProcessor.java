package com.javarush.khmelov.lesson04.processor.bpp;

import com.javarush.khmelov.lesson04.config.SessionCreator;
import com.javarush.khmelov.lesson04.processor.annotation.Tx;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class TxBeanAnnotationPostProcessor implements BeanPostProcessor {

    public static final Class<Tx> ANNOTATION_CLASS = Tx.class;
    private final Map<String, Class<?>> realClassesWithAnnotation = new ConcurrentHashMap<>();
    private final ApplicationContext applicationContext;
    private final SessionCreator sessionCreator;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (isAnnotationPresent(aClass)) {
            realClassesWithAnnotation.put(beanName, aClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (realClassesWithAnnotation.containsKey(beanName)) {
            return proxy(bean, realClassesWithAnnotation.get(beanName));
        }
        return bean;
    }

    private boolean isAnnotationPresent(Class<?> aClass) {
        return aClass.isAnnotationPresent(ANNOTATION_CLASS) || Arrays.stream(aClass.getMethods())
                .anyMatch(m -> m.isAnnotationPresent(ANNOTATION_CLASS));
    }


    private Object proxy(Object beanOrProxy, Class<?> beanRealClass) {
        MethodInterceptor handler = (obj, method, args, proxy) -> {
            Object result;
            if (beanRealClass.isAnnotationPresent(ANNOTATION_CLASS)
                || method.isAnnotationPresent(ANNOTATION_CLASS)
            ) {
                sessionCreator.beginTransactional();
                result = proxy.invoke(beanOrProxy, args);
                sessionCreator.endTransactional();
                return result;
            } else {
                return proxy.invoke(beanOrProxy, args);
            }
        };
        return enhancerCreate(beanRealClass, handler);
        //return Enhancer.create(beanRealClass, handler); //if empty constructor
    }

    private Object enhancerCreate(Class<?> beanRealClass, MethodInterceptor handler) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanRealClass);
        enhancer.setCallback(handler);
        Constructor<?> constructor = Arrays
                .stream(beanRealClass.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow();
        Object[] paramBeans = Arrays
                .stream(constructor.getParameterTypes())
                .map(applicationContext::getBean)
                .toArray();
        return enhancer.create(constructor.getParameterTypes(), paramBeans);
    }

}
