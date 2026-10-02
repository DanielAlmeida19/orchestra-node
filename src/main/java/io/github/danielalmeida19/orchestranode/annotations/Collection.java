package io.github.danielalmeida19.orchestranode.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to sinalize a Collection to be managed by OrchestraNode
 *
 * @author Daniel
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Collection {

    /**
     * The name that will be mapped from the class to the data collection
     *
     * The default value is the original name of the class
     */
    String name() default "";

    /**
     * Level of priority that the OrchestraNode should give to the data collection
     * during a migration
     * 
     * The default value is NORMAL
     */
    PriorityLevel priorityLevel() default PriorityLevel.NORMAL;

}
