package io.github.danielalmeida19.orchestranode.server;

import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.tools.JavaCompiler;

/**
 * DomainPackageManager
 */
public class DomainPackageManager {

    private final Path sourceRoot;
    private final String targetPackage;
    private final Path temporaryCompiledClassDir;
    private final JavaCompiler javaCompiler;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
}
