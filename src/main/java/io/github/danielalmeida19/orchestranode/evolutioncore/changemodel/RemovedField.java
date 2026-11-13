package io.github.danielalmeida19.orchestranode.evolutioncore.changemodel;

import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;

/**
 * Class that represents a removed field in a collection
 *
 * @author Daniel
 */
public class RemovedField {

    /**
     * Attribute that represents the removed field of the entity
     */
    private CollectionField<?> removedField;

    /**
     * Attribute that represents how the removed field will be mapped in the another
     * version
     */
    private RemovedFieldMigrationBehavior removedFieldMigrationBehavior;

    public RemovedField() {
    }

    public RemovedField(CollectionField<?> removedField, RemovedFieldMigrationBehavior removedFieldMigrationBehavior) {
        this.removedField = removedField;
        this.removedFieldMigrationBehavior = removedFieldMigrationBehavior;
    }

    public CollectionField<?> getRemovedField() {
        return removedField;
    }

    public void setRemovedField(CollectionField<?> removedField) {
        this.removedField = removedField;
    }

    public RemovedFieldMigrationBehavior getRemovedFieldMigrationBehavior() {
        return removedFieldMigrationBehavior;
    }

    public void setRemovedFieldMigrationBehavior(RemovedFieldMigrationBehavior removedFieldMigrationBehavior) {
        this.removedFieldMigrationBehavior = removedFieldMigrationBehavior;
    }

}
