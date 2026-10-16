package io.github.danielalmeida19.orchestranode.evolutioncore;

import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;

/**
 * Class that represents a change in the structure of a collection, with an
 * addition of a new Field
 *
 * @author Daniel
 */
public class FieldAdditionChange<T> {

    /**
     * Attributes that represents the new field of the entity
     */
    private CollectionField<T> newField;

    /**
     * Attribute that represents the default value of the new field
     */
    private T newDefaultValue;

    public CollectionField<T> getNewField() {
        return newField;
    }

    public void setNewField(CollectionField<T> newField) {
        this.newField = newField;
    }

    public T getNewDefaultValue() {
        return newDefaultValue;
    }

    public void setNewDefaultValue(T newDefaultValue) {
        this.newDefaultValue = newDefaultValue;
    }

    /**
     * This method apply the addition of the field in the collection
     */
    public void apply(Collection originalCollection) {
        originalCollection.getFields().add(newField);
    }

}
