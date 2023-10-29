package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseUtil {
    // setup for singleton class
    private static DatabaseUtil firstInstance = null;
    private DatabaseUtil() {};
    public static DatabaseUtil getInstance() {
        if (firstInstance == null) firstInstance = new DatabaseUtil();
        return firstInstance;
    }

    // DatabaseUtil's variables
    private static String databasePath = "/src/main/resources/database/";

    public static Connection connectToDatabase(String databaseName) {
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

    public static Connection closeDatabase(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    // getter and setters

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        DatabaseUtil.databasePath = databasePath;
    }
}
