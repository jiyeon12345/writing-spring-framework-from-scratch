package wsffs;

import wsffs.springframework.annotation.CustomRequestMapping;
import wsffs.springframework.beans.BeansException;
import wsffs.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import wsffs.springframework.context.ApplicationContext;
import wsffs.springframework.feature.user.UserController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class Application {

    public static void main(String[] args) {

        // 1. URL 정보 가져오기
        final String url = "/hello";

        // 2. AnnotationConfigServletWebServerApplicationContext를 생성
        final AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(Application.class);

        // 3. 모든 컴포넌트들을 가져옴
        Map<String, Object> beans = context.getBeansOfType(Object.class);

        // 4. URL 정보가 일치하는 메소드를 실행
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(CustomRequestMapping.class)) {
                    CustomRequestMapping annotation = method.getAnnotation(CustomRequestMapping.class);
                    String[] methodUrls = annotation.value();
                    for (String methodUrl : methodUrls) {
                        if (methodUrl.equals(url)) {
                            try {
                                method.invoke(bean);
                                return;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
