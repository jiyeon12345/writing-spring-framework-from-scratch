package wsffs.springframework.boot.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import wsffs.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import wsffs.springframework.web.servlet.DispatcherServlet;

public class JettyEmbeddedWebServer implements WebServer {

    private final Server server;
    private final AnnotationConfigServletWebServerApplicationContext applicationContext;

    private int port = -1;

    public JettyEmbeddedWebServer(int port, AnnotationConfigServletWebServerApplicationContext applicationContext) {
        this.port = port;
        this.server = new Server(port);
        this.applicationContext = applicationContext;
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        final DispatcherServlet dispatcherServlet = applicationContext
                .getBean("dispatcherServlet", DispatcherServlet.class);
        final ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
        context.addServlet(dispatcherServletHolder, "/*");
        server.setHandler(context);
    }

    @Override
    public void start() throws WebServerException {
        try {
            server.start();
        } catch (Exception e) {
            throw new WebServerException("웹서버 구동중에 문제가 발생하였습니다.", e);
        }
    }

    @Override
    public void stop() throws WebServerException {
        try {
            server.stop();
        } catch (Exception e) {
            throw new WebServerException("웹서버 종료중에 문제가 발생하였습니다.", e);
        }
    }

    @Override
    public int getPort() {
        return port;
    }
}
