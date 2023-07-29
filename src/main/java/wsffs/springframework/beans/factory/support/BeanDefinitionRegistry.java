package wsffs.springframework.beans.factory.support;

import wsffs.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    int getBeanDefinitionCount();

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);
}
