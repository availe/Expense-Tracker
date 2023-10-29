package Database;
import JavaFX.ExpenseRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public Connection connectToDatabase() {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath + databaseName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
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

    // not modular but we can try to figure this out later
    public void addRecord(ExpenseRecord record) {

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