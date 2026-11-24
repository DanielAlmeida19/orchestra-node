package io.github.danielalmeida19.orchestranode;

import java.util.List;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.migration.MigrationStrategy;

/**
 * Class that represents the general configuration of the Orchestra Node
 * approach
 *
 * @author Daniel
 */
public class OrchestraNodeConfiguration {

    /**
     * Attributes that represents the list of possible database connections
     */
    private List<Connection> databaseConnectionCandidates;

    /**
     * Attribute that represents the source package of the managed classes by
     * Orchestra Node
     */
    private String managedPackage;

    /**
     * Attribute that represents the path to the log file used by Orchestra Node
     */
    private String pathToLogFile;

    /**
     * Attribute that represents the number of modifications needed to generate a
     * new schema and database version
     */
    private int changesToNewVersion;

    /**
     * Attribute that represents the number of instances migrated by batch (set zero
     * to select "all")
     */
    private int batchSizeMigration;

    /**
     * Attribute to represents the migration strategy to between two Schema Versions
     */
    private MigrationStrategy migrationStrategy;

    /**
     * Attribute that represents the permitted size of the queue of requisitions
     * from another Orchestra Node
     */
    private int permittedQueueSize;

    public OrchestraNodeConfiguration() {
    }

    public OrchestraNodeConfiguration(List<Connection> databaseConnectionCandidates, String managedPackage,
            String pathToLogFile, int changesToNewVersion, int batchSizeMigration, MigrationStrategy migrationStrategy,
            int permittedQueueSize) {
        this.databaseConnectionCandidates = databaseConnectionCandidates;
        this.managedPackage = managedPackage;
        this.pathToLogFile = pathToLogFile;
        this.changesToNewVersion = changesToNewVersion;
        this.batchSizeMigration = batchSizeMigration;
        this.migrationStrategy = migrationStrategy;
        this.permittedQueueSize = permittedQueueSize;
    }

    public List<Connection> getDatabaseConnectionCandidates() {
        return databaseConnectionCandidates;
    }

    public void setDatabaseConnectionCandidates(List<Connection> databaseConnectionCandidates) {
        this.databaseConnectionCandidates = databaseConnectionCandidates;
    }

    public String getManagedPackage() {
        return managedPackage;
    }

    public void setManagedPackage(String managedPackage) {
        this.managedPackage = managedPackage;
    }

    public String getPathToLogFile() {
        return pathToLogFile;
    }

    public void setPathToLogFile(String pathToLogFile) {
        this.pathToLogFile = pathToLogFile;
    }

    public int getChangesToNewVersion() {
        return changesToNewVersion;
    }

    public void setChangesToNewVersion(int changesToNewVersion) {
        this.changesToNewVersion = changesToNewVersion;
    }

    public int getBatchSizeMigration() {
        return batchSizeMigration;
    }

    public void setBatchSizeMigration(int batchSizeMigration) {
        this.batchSizeMigration = batchSizeMigration;
    }

    public MigrationStrategy getMigrationStrategy() {
        return migrationStrategy;
    }

    public void setMigrationStrategy(MigrationStrategy migrationStrategy) {
        this.migrationStrategy = migrationStrategy;
    }

    public int getPermittedQueueSize() {
        return permittedQueueSize;
    }

    public void setPermittedQueueSize(int permittedQueueSize) {
        this.permittedQueueSize = permittedQueueSize;
    }

}
