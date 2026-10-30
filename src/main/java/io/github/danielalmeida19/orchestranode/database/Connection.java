package io.github.danielalmeida19.orchestranode.database;

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
     * The state of the connection, if is in use, available or not instatiated
     */
    private ConnectionState connectionState;

    /**
     * The name of the with access to database
     */
    private String user;

    /**
     * The password of the user
     */
    private String password;

    public Connection(
            OrchestraNodeDriver driver,
            String host,
            String port,
            String databaseName,
            ConnectionState connectionState,
            String user,
            String password) {
        this.driver = driver;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.connectionState = connectionState;
        this.user = user;
        this.password = password;
    }

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        sb.append(host).append(":");
        sb.append(port);

        return sb.toString();

    }

}
