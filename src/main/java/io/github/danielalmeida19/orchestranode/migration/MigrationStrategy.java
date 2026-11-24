package io.github.danielalmeida19.orchestranode.migration;

/**
 * Defines how the instances will be migrated from one database to another, that
 * means the time and the interval of waiting between migrations
 *
 * @author Daniel
 */
public enum MigrationStrategy {

    /**
     * All the instances will be migrated at the moment of the Schema Version
     * creation
     */
    EAGER,

    /**
     * The instances will be migrated to new Schema Version on demand by the client
     */
    LAZY
}
