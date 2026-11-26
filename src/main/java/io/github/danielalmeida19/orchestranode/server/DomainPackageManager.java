package io.github.danielalmeida19.orchestranode.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.OrchestraNode;
import io.github.danielalmeida19.orchestranode.evolutioncore.SchemaProcessor;

/**
 * DomainPackageManager
 */
public class DomainPackageManager {

    private final Path sourceRoot;
    private final String targetPackage;
    private final Path temporaryCompiledClassDir;
    private final JavaCompiler javaCompiler;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private DynamicDomainClassLoader currentClassLoader;
    private final Map<String, Class<?>> loadedDomainClasses = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(DomainPackageManager.class);

    public DomainPackageManager(Path sourceRoot, String targetPackage) throws IOException {
        this.sourceRoot = sourceRoot;
        this.targetPackage = targetPackage;
        this.temporaryCompiledClassDir = Files.createTempDirectory("domain_hot_reload_");
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();

        if (javaCompiler == null) {
            throw new IllegalStateException(
                    "JDK required! The current environment is running only a JRE, without javac.");
        }

        // Guarantee the clean of temporaries files when JVM ends.
        this.temporaryCompiledClassDir.toFile().deleteOnExit();
    }

    public void rebuildAndInspect() throws IOException {
        Path packageDir = sourceRoot.resolve(targetPackage.replace('.', File.separatorChar));

        List<File> javaFiles;
        try (Stream<Path> walk = Files.walk(packageDir)) {
            javaFiles = walk.filter(p -> p.toString().endsWith(".java"))
                    .map(Path::toFile).collect(Collectors.toList());
        }

        if (javaFiles.isEmpty()) {
            return;
        }

        // 1. Compilation using the native Javax.tools API
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, null, null)) {

            // Sets the temporary directory as the output (-d) and inherits the
            // application's current classpath (-cp)
            List<String> options = List.of(
                    "-d", temporaryCompiledClassDir.toAbsolutePath().toString(),
                    "-cp", System.getProperty("java.class.path"));

            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, diagnostics, options, null,
                    compilationUnits);

            boolean success = task.call();

            if (!success) {
                // The client saved a file with a syntax error: this does not crash the server;
                // it simply logs the error and aborts the swap.
                diagnostics.getDiagnostics().forEach(d -> log
                        .error("[Server Compilation Error] Line: " + d.getLineNumber() + ": " + d.getMessage(null)));
                return;
            }

            // 2. ClassLoader Hot Swap and inspection of new structure
            lock.writeLock().lock();
            try {
                if (currentClassLoader != null) {
                    currentClassLoader.close();
                }

                URL[] urls = { temporaryCompiledClassDir.toUri().toURL() };
                currentClassLoader = new DynamicDomainClassLoader(urls, Thread.currentThread().getContextClassLoader(),
                        targetPackage);
                loadedDomainClasses.clear();

                for (File javaFile : javaFiles) {
                    String className = resolveClassName(packageDir, javaFile.toPath());
                    Class<?> clazz = currentClassLoader.loadClass(className);
                    loadedDomainClasses.put(className, clazz);
                    OrchestraNode.addCollection(SchemaProcessor.processClass(clazz));
                }

            } catch (ClassNotFoundException e) {
                log.error(e.getStackTrace().toString());
            } finally {
                lock.writeLock().unlock();
            }

        }

    }

    private String resolveClassName(Path packageDir, Path javaFile) {
        Path relative = sourceRoot.relativize(javaFile);
        String pathString = relative.toString().replace(File.separatorChar, '.');
        return pathString.substring(0, pathString.length() - 5);
    }

}
