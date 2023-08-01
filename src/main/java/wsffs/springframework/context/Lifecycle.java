package wsffs.springframework.context;

public interface Lifecycle {

    void start();

    void stop();

    boolean isRunning();
}
