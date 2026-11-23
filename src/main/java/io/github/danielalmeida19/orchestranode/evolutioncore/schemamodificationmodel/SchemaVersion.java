package io.github.danielalmeida19.orchestranode.evolutioncore.schemamodificationmodel;

import java.util.Date;
import java.util.List;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;

/**
 * Class that represents a Schema Version created and managed by the
 * OrchestraNode middleware, with all collections managed and imported from the
 * classes and a list of modification recieved by the previous version.
 *
 * Also, a Schema Version is related by a connection with a database, once a new
 * schema version generated a new database version
 *
 *
 * @author Daniel
 */
public class SchemaVersion {

    /**
     * Attributes that represents the date and the time of the creation of the new
     * version
     */
    private Date dateTimeVersion;

    /**
     * Attribute that represents the number of the version
     */
    private int versionNumber;

    /**
     * Attributes that represents the list of the collections present in the Schema
     * Version
     */
    private List<Collection> collections;

    /**
     * Attribute that represents the list of schema modifications from the previous
     * version (it will be null if is the first version)
     */
    private List<SchemaModification> schemaModifications;

    /**
     * Attribute that represents the connection with the database used for this
     * schema version
     */
    private Connection connection;

    /**
     * Attribute that represents if the SchemaVersion of the Extracted collections
     * are syncronized with the schema present in the database
     */
    private boolean syncronized;

    /**
     * Attribute that represents if the this schema version are active in some
     * database
     */
    private boolean active;

    public SchemaVersion() {
    }

    public SchemaVersion(Date dateTimeVersion, int versionNumber, List<Collection> collections,
            List<SchemaModification> schemaModifications,
            Connection connection, boolean syncronized, boolean active) {
        this.dateTimeVersion = dateTimeVersion;
        this.versionNumber = versionNumber;
        this.collections = collections;
        this.schemaModifications = schemaModifications;
        this.connection = connection;
        this.syncronized = syncronized;
        this.active = active;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public List<SchemaModification> getSchemaModifications() {
        return schemaModifications;
    }

    public void setSchemaModifications(List<SchemaModification> schemaModifications) {
        this.schemaModifications = schemaModifications;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isSyncronized() {
        return syncronized;
    }

    public void setSyncronized(boolean syncronized) {
        this.syncronized = syncronized;
    }

    public Date getDateTimeVersion() {
        return dateTimeVersion;
    }

    public void setDateTimeVersion(Date dateTimeVersion) {
        this.dateTimeVersion = dateTimeVersion;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
