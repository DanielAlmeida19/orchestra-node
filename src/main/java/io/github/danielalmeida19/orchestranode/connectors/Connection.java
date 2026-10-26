package io.github.danielalmeida19.orchestranode.connectors;

/**
 * This class represents a Connection of a DB to the middleware
 *
 * @author Daniel
 */
public class Connection {

    /**
     * The DBMS used in the connection
     */
    private OrchestraNodeDriver driver;

    /**
     * IP address of the server hosting the database
     */
    private String host;

    /**
     * The port of connection of the database
     */
    private String port;

    /**
     * The database name of the
     */
    private String databaseName;

    /**
     * The state of the connection, if is i
     */
    private ConnectionState connectionState;

    public OrchestraNodeDriver getDriver() {
        return driver;
    }

    public void setDriver(OrchestraNodeDriver driver) {
        this.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public String getURI() {
        StringBuilder sb = new StringBuilder();
        switch (driver) {
            case MONGO:
                sb.append("mongodb");
                break;

            default:
                break;
        }

        sb.append("://");
    }

}
