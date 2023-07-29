package wsffs.springframework.boot.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import wsffs.springframework.web.servlet.DispatcherServlet;

public class JettyEmbeddedWebServer implements WebServer {

    private int port = -1;
    private final Server server;

    public JettyEmbeddedWebServer(int port) {
        if (port > 0) {
            final ServletContextHandler contextHandler = new ServletContextHandler();
            contextHandler.setContextPath("/");
            final DispatcherServlet dispatcherServlet = new DispatcherServlet();
            final ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
            contextHandler.addServlet(dispatcherServletHolder, "/*");
            final Server server = new Server(port);
            server.setHandler(contextHandler);
            this.port = port;
            this.server = server;
        } else {
            throw new RuntimeException("포트 번호 지정 안함");
        }
    }

    @Override
    public void start() throws WebServerException {
        try {
            server.start();
        } catch (Exception e) {
            throw new WebServerException("웹 서버를 실행하는 도중에 문제가 발생하였습니다.", e);
        }
    }

    @Override
    public void stop() throws WebServerException {
        try {
            server.stop();
        } catch (Exception e) {
            throw new WebServerException("웹 서버를 정지하는 도중에 문제가 발생하였습니다.", e);
        }
    }
}
