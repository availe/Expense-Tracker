package Database;
import JavaFX.ExpenseRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String loading = "jdbc:sqlite:" + databasePath + databaseName;
            System.out.println(loading);
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

    // not modular but we can try to figure this out later
    public void addRecord(ExpenseRecord record) {
        int departmentID = getDepartmentID(record.getDepartment());
        if (departmentID == -1) {
            System.out.println("DepartmentID was not found");
            return;
        }

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "INSERT INTO expenses (departmentId, amount, date, category) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = DatabaseUtil.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, departmentID);
            preparedStatement.setDouble(2, record.getAmount());
            preparedStatement.setString(3, record.getDate());
            preparedStatement.setString(4, record.getCategory());
            preparedStatement.setString(5, record.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Write was successful");;
        } catch (Exception e) {
            System.out.println("Write was not successful");;
            System.out.println(e.toString());
        }
    }

    public int getDepartmentID(String departmentName) {
        String query = "select departmentId from departments where departmentName = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DatabaseUtil.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, departmentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("departmentId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<ExpenseRecord> readRecords() {
        List<ExpenseRecord> expenses = new ArrayList<>();
        String query = "select e.amount, e.date, e.category, e.description, d.departmentName from expenses e join departments d on e.departmentId = d.departmentId";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");
                String category = resultSet.getString("category");
                String department = resultSet.getString("departmentName");
                String description = resultSet.getString("description");

                expenses.add(new ExpenseRecord(amount, category, date, department, description));
                System.out.println("Read was successful");
            }
            } catch (SQLException e) {
            System.out.println("Read was not successful");
            throw new RuntimeException(e);
        }
        return expenses;
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