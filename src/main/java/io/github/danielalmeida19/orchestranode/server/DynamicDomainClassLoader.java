package io.github.danielalmeida19.orchestranode.server;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * DynamicDomainClassLoader
 */
public class DynamicDomainClassLoader extends URLClassLoader {

    private final String domainPackagePrefix;

    public DynamicDomainClassLoader(URL[] urls, ClassLoader parent, String domainPackage) {
        super(urls, parent);
        this.domainPackagePrefix = domainPackage + ".";
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> loaded = findLoadedClass(name);

            if (loaded != null) {
                return loaded;
            }

            // Intercepts JUST the domain package classes to search in temporary directory
            // first

        }
    }
}
