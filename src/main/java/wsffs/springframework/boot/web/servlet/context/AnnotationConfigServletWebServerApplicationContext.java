package wsffs.springframework.boot.web.servlet.context;

import javassist.tools.reflect.Reflection;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import wsffs.Application;
import wsffs.springframework.annotation.Component;
import wsffs.springframework.annotation.CustomRequestMapping;
import wsffs.springframework.beans.BeansException;
import wsffs.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationConfigServletWebServerApplicationContext  implements ApplicationContext {

    private Set<Class<?>> candidates;
    private final Map<String, Class<?>> candidatesByBeanName = new HashMap<>();
    private final Map<String, Object> beans = new HashMap<>();
    private final Set<String> customRequestUrls = new HashSet<>();
    public AnnotationConfigServletWebServerApplicationContext(Class<?> primarySource){
        scan(primarySource);
    }

    public void scan(Class<?> primarySource){
        final String packageToScan = primarySource.getPackageName();
        final Reflections reflections = new Reflections(packageToScan, Scanners.TypesAnnotated);
        candidates = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> candidate : candidates) {
            final String beanName = determineBeanNameByClass(candidate);
            candidatesByBeanName.put(beanName,candidate);

            // CustomRequestMapping 어노테이션 정보 수집
            collectCustomRequestMappingUrls(candidate);
        }
    }

    private String determineBeanNameByClass(Class<?> clazz) {
        //bean이름이 userController로 앞글자가 소문자인데, Component를 가지고있는 클래스 이름이 대문자여서 소문자로 변경하기 위한 작업
        String originName = clazz.getSimpleName();
        String rest = originName.substring(1);
        String first = originName.substring(0, 1).toLowerCase();

        return first + rest;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        final Object maybeBean = beans.get(beanName);
        if (maybeBean != null) {
            return requiredType.cast(maybeBean);
        }

        final Class<?> classToInstance = candidatesByBeanName.get(beanName);
        final Constructor<?> ctor = classToInstance.getDeclaredConstructors()[0];
        if (ctor.getParameterCount() == 0) {
            return createBeanInstance(beanName, requiredType, ctor);
        }

        final List<Object> deps = createDependencies(ctor);
        return createBeanInstance(beanName, requiredType, ctor, deps.toArray());
    }

    private <T> T createBeanInstance(String beanName, Class<T> requiredType, Constructor<?> ctor, Object... args) {
        try {
            final Object newInstance = ctor.newInstance(args);
            beans.put(beanName, newInstance);
            return requiredType.cast(newInstance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Object> createDependencies(Constructor<?> ctor) {
        final List<Object> deps = new ArrayList<>();
        final Class<?>[] parameterTypes = ctor.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            final String beanNameOfDep = determineBeanNameByClass(parameterType);
            final Object dependency = getBean(beanNameOfDep, parameterType);
            System.out.println("dependency = " + dependency);
            deps.add(dependency);
        }
        return deps;
    }

    public Map<String, Object> getBeansOfType(Class<?> type) {

        Map<String, Object> beansOfType = new HashMap<>();
        for (Map.Entry<String, Class<?>> entry : candidatesByBeanName.entrySet()) {
            String beanName = entry.getKey();
            Class<?> beanClass = entry.getValue();
            if (type.isAssignableFrom(beanClass)) {
                Object bean = getBean(beanName, Object.class);
                beansOfType.put(beanName, bean);
            }
        }
        return beansOfType;
    }

    private void collectCustomRequestMappingUrls(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(CustomRequestMapping.class)) {
                CustomRequestMapping annotation = method.getAnnotation(CustomRequestMapping.class);
                String[] methodUrls = annotation.value();
                customRequestUrls.addAll(Arrays.asList(methodUrls));
            }
        }
    }
}
