package wsffs.springframework.boot.web.embedded;

import wsffs.springframework.boot.web.server.JettyEmbeddedWebServer;
import wsffs.springframework.boot.web.server.ServletWebServerFactory;
import wsffs.springframework.boot.web.server.WebServer;
import wsffs.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

public class JettyServletWebServerFactory implements ServletWebServerFactory {

    private final AnnotationConfigServletWebServerApplicationContext applicationContext;

    public JettyServletWebServerFactory(AnnotationConfigServletWebServerApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public WebServer getWebServer() {
        return new JettyEmbeddedWebServer(8080, this.applicationContext);
    }
}
