package io.github.danielalmeida19.orchestranode.evolutioncore.schemamodificationmodel;

import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;

/**
 * Class that represents the Schema Modification of a Collection
 *
 * @author Daniel
 */
public class SchemaModification {

    /**
     * Attribute that represents if the schema modification are migrated
     */
    private boolean migrated;

    /**
     * Attribute that represents the previous version of the collection
     */
    private Collection before;

    /**
     * Attribute that represents the next version of the collection
     */
    private Collection after;

    /**
     * Attributes that represents the modification delta between the two versions
     * and the correct mapping
     */
    private DeltaSchemaModification deltaSchemaModification;

    public SchemaModification() {
    }

    public SchemaModification(boolean migrated, Collection before, Collection after,
            DeltaSchemaModification deltaSchemaModification) {
        this.migrated = migrated;
        this.before = before;
        this.after = after;
        this.deltaSchemaModification = deltaSchemaModification;
    }

    public boolean isMigrated() {
        return migrated;
    }

    public void setMigrated(boolean migrated) {
        this.migrated = migrated;
    }

    public Collection getBefore() {
        return before;
    }

    public void setBefore(Collection before) {
        this.before = before;
    }

    public Collection getAfter() {
        return after;
    }

    public void setAfter(Collection after) {
        this.after = after;
    }

    public DeltaSchemaModification getDeltaSchemaModification() {
        return deltaSchemaModification;
    }

    public void setDeltaSchemaModification(DeltaSchemaModification deltaSchemaModification) {
        this.deltaSchemaModification = deltaSchemaModification;
    }

}
