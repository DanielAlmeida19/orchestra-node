package io.github.danielalmeida19.orchestranode.database;

/**
 * Enum that represents the state of the current Connection
 * 
 * @author Daniel
 */
public enum ConnectionState {

    /**
     * When the database in the IP address is not created or the IP address is not
     * available yet
     */
    NOT_INSTANTIATED,

    /**
     * When connection is instantiated but is in use for another version
     */
    USED,

    /**
     * When the connection is instantiated and available to be used.
     */
    AVAILABLE

}
