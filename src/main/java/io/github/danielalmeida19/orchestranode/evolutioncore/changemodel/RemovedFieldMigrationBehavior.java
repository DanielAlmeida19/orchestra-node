package io.github.danielalmeida19.orchestranode.evolutioncore.changemodel;

/**
 * RemovedFieldMigrationBehavior
 */
public enum RemovedFieldMigrationBehavior {

    /**
     * The field will be removed in the new generated schema version
     */
    REMOVE,

    /**
     * The new field will be populated as null in the new generated schema version
     */
    SET_NULL,

    /**
     * The new field will be populated as zero in the new generated schema version
     */
    SET_ZERO,

    /**
     * The new field will be populated as an empty list in the new generated schema
     * version
     */
    SET_EMPTY_LIST,

    /**
     * The new field will be setted as an empty object in the new generated schema
     * version
     */
    SET_EMPTY_OBJECT
}
