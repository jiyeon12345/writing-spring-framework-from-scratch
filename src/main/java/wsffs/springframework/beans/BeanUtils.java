package wsffs.springframework.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BeanUtils {

    public static Constructor<?> getCandidateConstructor(Class<?> clazz) {
        return clazz.getDeclaredConstructors()[0];
    }

    public static <T> T instantiate(
            Constructor<?> constructor,
            Class<T> requiredType,
            Object... initargs
    ) {
        try {
            final Object newInstance = constructor.newInstance(initargs);
            return requiredType.cast(newInstance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Critical error occurred during instantiation!");
        }
    }
}
