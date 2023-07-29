package wsffs.springframework.beans.factory.support;

public interface BeanNameGenerator {

    String generateBeanNameFromClass(Class<?> clazz);
}
