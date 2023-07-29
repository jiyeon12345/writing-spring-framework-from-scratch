package wsffs.springframework.context.annotation;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import wsffs.springframework.beans.BeanUtils;
import wsffs.springframework.beans.factory.config.BeanDefinition;
import wsffs.springframework.beans.factory.config.DefaultBeanDefinition;
import wsffs.springframework.beans.factory.support.BeanDefinitionRegistry;
import wsffs.springframework.beans.factory.support.BeanNameGenerator;
import wsffs.springframework.beans.factory.support.DefaultBeanNameGenerator;
import wsffs.springframework.streotype.Component;

import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * A bean definition scanner that detects bean candidates on the classpath,
 * registering corresponding bean definitions with a given registry ({@code BeanFactory}
 * or {@code ApplicationContext}).
 * <p>
 * classpath 상의 bean 후보를 탐지하는 bean definition(빈 정의) 스캐너입니다.
 * 주어진 레지스트리(BeanFactory)나 ApplicationContext로 해당하는 bean 정의들을 등록합니다.
 *
 * <p>Candidate classes are detected through configurable type filters. The
 * default filters include classes that are annotated with Spring's
 * {@link wsffs.springframework.streotype.Component @Component},
 * {@link wsffs.springframework.streotype.Repository @Repository},
 * {@link wsffs.springframework.streotype.Service @Service}, or
 * {@link wsffs.springframework.streotype.Controller @Controller} stereotype.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Chris Beams
 * @see AnnotationConfigApplicationContext#scan
 * @see wsffs.springframework.streotype.Component
 * @see wsffs.springframework.streotype.Repository
 * @see wsffs.springframework.streotype.Service
 * @see wsffs.springframework.streotype.Controller
 * @since 2.5
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private final BeanNameGenerator beanNameGenerator = DefaultBeanNameGenerator.INSTANCE;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public int scan(String... basePackages) {
        final int beanCountAtScanStart = registry.getBeanDefinitionCount();

        for (String basePackage : basePackages) {
            final Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);
            final Set<Class<?>> candidates = reflections.getTypesAnnotatedWith(Component.class);
            for (Class<?> candidate : candidates) {
                final BeanDefinition dbd = new DefaultBeanDefinition();
                dbd.setBeanClass(candidate);

                final Constructor<?> constructor = BeanUtils.getCandidateConstructor(candidate);
                final Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (Class<?> parameterType : parameterTypes) {
                    final String beanNameOfDependency = beanNameGenerator.generateBeanNameFromClass(parameterType);
                    dbd.setDependsOn(beanNameOfDependency);
                }

                final String beanNameFromClass = beanNameGenerator.generateBeanNameFromClass(candidate);
                registry.registerBeanDefinition(beanNameFromClass, dbd);
            }
        }

        return (registry.getBeanDefinitionCount() - beanCountAtScanStart);
    }
}
