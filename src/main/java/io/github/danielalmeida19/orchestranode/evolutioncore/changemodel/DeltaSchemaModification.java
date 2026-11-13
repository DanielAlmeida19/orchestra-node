package io.github.danielalmeida19.orchestranode.evolutioncore.changemodel;

import java.util.List;

/**
 * DeltaSchemaModification
 */
public class DeltaSchemaModification {

    /**
     * Attribute that represents if the collection will be renamed
     */
    private boolean renamed;

    /**
     * Attribute that represents if the collection will be removed
     */
    private boolean removed;

    /**
     * Attribute that represents the added fields in the collection
     */
    private List<AddedField> addedFields;

    /**
     * Attribute that represents the removed fields in the collection
     */
    private List<RemovedField> removedFields;

    public DeltaSchemaModification() {
    }

    public DeltaSchemaModification(boolean renamed, boolean removed, List<AddedField> addedFields,
            List<RemovedField> removedFields) {
        this.renamed = renamed;
        this.removed = removed;
        this.addedFields = addedFields;
        this.removedFields = removedFields;
    }

    public boolean isRenamed() {
        return renamed;
    }

    public void setRenamed(boolean renamed) {
        this.renamed = renamed;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public List<AddedField> getAddedFields() {
        return addedFields;
    }

    public void setAddedFields(List<AddedField> addedFields) {
        this.addedFields = addedFields;
    }

    public List<RemovedField> getRemovedFields() {
        return removedFields;
    }

    public void setRemovedFields(List<RemovedField> removedFields) {
        this.removedFields = removedFields;
    }
}
