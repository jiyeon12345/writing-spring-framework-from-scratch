package wsffs.springframework.beans.factory.support;

public class DefaultBeanNameGenerator implements BeanNameGenerator {

    public static BeanNameGenerator INSTANCE = new DefaultBeanNameGenerator();

    @Override
    public String generateBeanNameFromClass(Class<?> clazz) {
        final String simpleName = clazz.getSimpleName();
        final String first = simpleName.substring(0, 1);
        final String rest = simpleName.substring(1);
        return first.toLowerCase() + rest;
    }
}
