package io.github.danielalmeida19.orchestranode.annotations;

/**
 * Defines the priority level used to define the order of migration for each
 * one of the collections.
 *
 * @author Daniel
 */
public enum PriorityLevel {

    /**
     * The collections marked with low priority will be the last to be migrated
     */
    LOW,

    /**
     * The collections marked with normal priority will be migrated after the
     * collections
     * with high priority.
     */
    NORMAL,

    /**
     * The collections marked with high priority will be the first to be migrated.
     */
    HIGH
}
