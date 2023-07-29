package wsffs;

import wsffs.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

public class Application {

    public static void main(String[] args) {
        final AnnotationConfigServletWebServerApplicationContext annotationConfigServletWebServerApplicationContext =
                new AnnotationConfigServletWebServerApplicationContext(
                        Application.class.getPackageName()
                );

        annotationConfigServletWebServerApplicationContext.refresh();
    }
}
