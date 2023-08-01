package wsffs.springframework.boot.web.servlet.context;

import wsffs.springframework.boot.web.server.WebServer;
import wsffs.springframework.context.Lifecycle;

public class WebServerStartStopLifecycle implements Lifecycle {

    private final AnnotationConfigServletWebServerApplicationContext applicationContext;
    private final WebServer webServer;
    private boolean isRunning = false;

    public WebServerStartStopLifecycle(AnnotationConfigServletWebServerApplicationContext applicationContext, WebServer webServer) {
        this.applicationContext = applicationContext;
        this.webServer = webServer;
    }

    @Override
    public void start() {
        isRunning = true;
        webServer.start();
    }

    @Override
    public void stop() {
        isRunning = false;
        webServer.stop();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
