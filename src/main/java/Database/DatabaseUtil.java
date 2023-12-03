package Database;

import java.sql.*;

public final class DatabaseUtil {
    // setup for singleton class
    private static DatabaseUtil firstInstance = null;

    private DatabaseUtil() {
    }

    public static DatabaseUtil getInstance() {
        if (firstInstance == null) firstInstance = new DatabaseUtil();
        return firstInstance;
    }

    // DatabaseUtil's variables
    private String databasePath = "databasePath is not initialized";
    private String databaseName = "databaseName is not initialized";
    private Connection connection = null;

    // DatabaseUtil's methods

    public void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath + databaseName);
            Statement statement = this.connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeDatabase() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isDatabaseConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // getter and setters

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}