package io.github.danielalmeida19.orchestranode.annotations;

/**
 * Defines how the ID Field will be fill in.
 *
 * @author Daniel
 */
public enum FillingPolicy {

    /**
     * The IDs marked with DB_AUTO_GENERATED will use the auto fill based on the
     * DBMS mechanism to save the instance
     */
    DB_AUTO_GENERATED,

    /**
     * The IDs marked with MANUALLY will use the value set in the object in memory
     * to save the instance
     */
    MANUALLY
}
