package wsffs.springframework.boot.web.servlet.context;

import wsffs.springframework.boot.web.server.WebServer;
import wsffs.springframework.context.Lifecycle;

public class WebServerStartStopLifecycle implements Lifecycle {

    private boolean isRunning = false;
    private final WebServer webServer;

    public WebServerStartStopLifecycle(WebServer webServer) {
        this.webServer = webServer;
    }

    @Override
    public void start() {
        webServer.start();
        isRunning = true;
    }

    @Override
    public void stop() {
        webServer.stop();
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
