package io.github.danielalmeida19.orchestranode.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to defines the package of the application on main class to pick
 * the classes to be managed by OrchestraNode
 *
 * @author Daniel
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrchestraNodeManagedPackage {

    /**
     * The package that will be used to search the collections managed by
     * OrchestraNode
     */
    String managedPackage() default "";
}
