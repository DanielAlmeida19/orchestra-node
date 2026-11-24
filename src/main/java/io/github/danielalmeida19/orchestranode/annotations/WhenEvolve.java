package io.github.danielalmeida19.orchestranode.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.danielalmeida19.orchestranode.evolutioncore.schemamodificationmodel.AddedFieldMigrationBehavior;
import io.github.danielalmeida19.orchestranode.evolutioncore.schemamodificationmodel.RemovedFieldMigrationBehavior;

/**
 * Annotation to sinalize how the fields will be evolved
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface WhenEvolve {

    /**
     * Define how the added fields will be filled in the next schema version, if
     * applied on a class, represents a default for all internal fields
     */
    AddedFieldMigrationBehavior addedFieldMigrationBehavior() default AddedFieldMigrationBehavior.SET_NULL;

    /**
     * Define how the removed field will be appear in the next schema version, if
     * applied on a class, represents a default for all internal fields
     */
    RemovedFieldMigrationBehavior removedFieldMigrationBehavior() default RemovedFieldMigrationBehavior.REMOVE;
}
