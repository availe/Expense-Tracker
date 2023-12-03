package Database;

import Core.ExpenseRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ExpenseManager {
    private static Connection connection = DatabaseUtil.getInstance().getConnection();
    // setup for singleton class
    private static ExpenseManager firstInstance = null;

    private ExpenseManager() {
    }

    public static ExpenseManager getInstance() {
        if (firstInstance == null) firstInstance = new ExpenseManager();
        return firstInstance;
    }

    // methods
    public void addRecord(ExpenseRecord record) {
        int departmentID = getDepartmentID(record.getDepartment());
        if (departmentID == -1) {
            System.out.println("DepartmentID was not found");
            return;
        }

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "INSERT INTO expenses (expenseID, departmentId, amount, date, category, description) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, record.getExpenseID());
            preparedStatement.setInt(2, departmentID);
            preparedStatement.setDouble(3, record.getAmount());
            preparedStatement.setString(4, record.getDate());
            preparedStatement.setString(5, record.getCategory());
            preparedStatement.setString(6, record.getDescription());
            preparedStatement.executeUpdate();
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
        String query = "select e.expenseId, e.amount, e.date, e.category, e.description, d.departmentName from expenses e join departments d on e.departmentId = d.departmentId";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer expenseId = resultSet.getInt("expenseId");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");
                String category = resultSet.getString("category");
                String department = resultSet.getString("departmentName");
                String description = resultSet.getString("description");

                expenses.add(new ExpenseRecord(expenseId, amount, category, date, department, description));
            }
        } catch (SQLException e) {
            System.out.println("Read was not successful");
            throw new RuntimeException(e);
        }
        return expenses;
    }

    public void deleteRecord(ExpenseRecord record) {
        String query = "delete from expenses where expenseId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, record.getExpenseID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
