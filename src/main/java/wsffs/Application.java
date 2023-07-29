package wsffs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import wsffs.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        final AnnotationConfigServletWebServerApplicationContext annotationConfigServletWebServerApplicationContext =
                new AnnotationConfigServletWebServerApplicationContext(
                        Application.class.getPackageName()
                );

        annotationConfigServletWebServerApplicationContext.refresh();

        /*final Server server = new Server(8080);
        final ServletContextHandler contextHandler = new ServletContextHandler();
        final DispatcherServlet dispatcherServlet = new DispatcherServlet();
        final ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(dispatcherServletHolder, "/*");
        server.setHandler(contextHandler);
        server.start();*/
    }
}
