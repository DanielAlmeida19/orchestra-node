package io.github.danielalmeida19.orchestranode.server;

import java.nio.file.Path;

/**
 * OrchestraServer
 */
public class OrchestraServer implements Runnable {

    private final Path packagePath;
    private volatile boolean running = false;

    @Override
    public void run() {

    }

    public void stop() {
        running = false;
    }
}
