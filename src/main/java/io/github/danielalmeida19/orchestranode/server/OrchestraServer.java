package io.github.danielalmeida19.orchestranode.server;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OrchestraServer
 */
public class OrchestraServer implements Runnable {

    private final Path packagePath;
    private final DomainPackageManager packageManager;
    private volatile boolean running = false;

    private static final Logger log = LoggerFactory.getLogger(OrchestraServer.class);

    public OrchestraServer(Path sourceRoot, String targetPackage, DomainPackageManager packageManager) {
        this.packagePath = sourceRoot.resolve(targetPackage.replace('.', '/'));
        this.packageManager = packageManager;
    }

    @Override
    public void run() {

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            // Register the directory (for subpackages, iterate using Files.walk and
            // register each subdirectory)
            packagePath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            // Initial compilation startup the server
            packageManager.rebuildAndInspect();

            while (running) {
                WatchKey key = watchService.poll(500, TimeUnit.MILLISECONDS);
                if (key == null) {
                    continue;
                }

                boolean shouldRebuild = false;
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changed = (Path) event.context();
                    if (changed != null && changed.toString().endsWith(".java")) {
                        shouldRebuild = true;
                    }
                }

                key.reset();

                if (shouldRebuild) {

                    // Short debounce: wait IDE finish to save the file
                    Thread.sleep(150);

                    // Drains duplicate events that accumulated during sleep
                    while ((key = watchService.poll()) != null) {
                        key.pollEvents();
                        key.reset();
                    }

                    log.info("Change detected, recompiling domain...");
                    packageManager.rebuildAndInspect();

                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void stop() {
        running = false;
    }
}
