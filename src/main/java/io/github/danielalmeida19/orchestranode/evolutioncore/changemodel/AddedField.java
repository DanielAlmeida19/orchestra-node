package io.github.danielalmeida19.orchestranode.evolutioncore.changemodel;

import java.util.Optional;

import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;

/**
 * Class that represents an added field in a collection
 *
 * @author Daniel
 */
public class AddedField {

    /**
     * Attributes that represents the new field of the entity
     */
    private CollectionField<?> addedField;

    /**
     * Attrubute that represents how the new field will be mapped in the another
     * version
     */
    private AddedFieldMigrationBehavior addedFieldMigrationBehavior;

    /**
     * Attribute that represents the source field of the value
     */
    private Optional<CollectionField<?>> sourceField;

    public AddedField() {
    }

    public AddedField(CollectionField<?> addedField, AddedFieldMigrationBehavior addedFieldMigrationBehavior) {
        this.addedField = addedField;
        this.addedFieldMigrationBehavior = addedFieldMigrationBehavior;
    }

    public CollectionField<?> getAddedField() {
        return addedField;
    }

    public void setAddedField(CollectionField<?> addedField) {
        this.addedField = addedField;
    }

    public AddedFieldMigrationBehavior getAddedFieldMigrationBehavior() {
        return addedFieldMigrationBehavior;
    }

    public void setAddedFieldMigrationBehavior(AddedFieldMigrationBehavior addedFieldMigrationBehavior) {
        this.addedFieldMigrationBehavior = addedFieldMigrationBehavior;
    }

    public Optional<CollectionField<?>> getSourceField() {
        return sourceField;
    }

    public void setSourceField(Optional<CollectionField<?>> sourceField) {
        this.sourceField = sourceField;
    }

}
