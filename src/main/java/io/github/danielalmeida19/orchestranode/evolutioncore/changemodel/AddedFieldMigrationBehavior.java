package io.github.danielalmeida19.orchestranode.evolutioncore.changemodel;

/**
 * Sinalizes how the Added Field will be filled in the next version of the
 * collection
 *
 * @author Daniel
 */
public enum AddedFieldMigrationBehavior {

    /**
     * The field will still be ignored and the modification will maintain just in
     * memory (this does not generate a new version)
     */
    IGNORE,

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
    SET_EMPTY_OBJECT,

    /**
     * The new field will be setted as a value from another field
     */
    FROM_ANOTHER_FIELD
}
