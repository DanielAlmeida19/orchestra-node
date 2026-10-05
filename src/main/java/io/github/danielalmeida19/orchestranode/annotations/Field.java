package io.github.danielalmeida19.orchestranode.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to sinalize a Field to be managed by OrchestraNode
 *
 * @author Daniel
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

    /**
     * The name that will be mapped from the field to the data structured
     */
    String name() default "";

    /**
     * External collection that the attribute references (will cause an error if it
     * does not point to an existing collection)
     */
    String external() default "";

    /**
     * Whether it is allowed for the attribute to contain empty values
     * (application-level check!!!)
     */
    boolean nullable() default true;

    /**
     * Whether it is allowed for the attribute to not be explicitly present in the
     * data structure
     */
    boolean ignorable() default true;
}
