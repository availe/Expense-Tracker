package Database;

import Core.UserRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class UserManager {
    private static Connection connection = DatabaseUtil.getInstance().getConnection();

    // setup for singleton class
    private static UserManager firstInstance = null;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (firstInstance == null) firstInstance = new UserManager();
        return firstInstance;
    }

    // methods
    public List<UserRecord> readUsers() throws SQLException {
        List<UserRecord> users = new ArrayList<>();
        String query = "select * from users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(new UserRecord(
                        resultSet.getInt("userId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("createdOn"),
                        resultSet.getString("lastLogin"),
                        resultSet.getBoolean("isManager"),
                        resultSet.getBoolean("isRoot"),
                        resultSet.getString("passHash")));
            }
        }
        return users;
    }

    public void addUser(UserRecord newUser) throws SQLException {
        String query = "insert into users (firstName, lastName, email, passHash, createdOn, lastLogin, isManager, isRoot) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newUser.getFirstName());
            preparedStatement.setString(2, newUser.getLastName());
            preparedStatement.setString(3, newUser.getEmail());
            preparedStatement.setString(4, newUser.getPassHash());
            preparedStatement.setString(5, newUser.getCreatedOn());
            preparedStatement.setString(6, newUser.getLastLogin());
            preparedStatement.setBoolean(7, newUser.getManager());
            preparedStatement.setBoolean(8, newUser.getRoot());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        String query = "delete from users where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void upgradeUserToManager(int userId) throws SQLException {
        String query = "update users set isManager = true where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void downgradeUserFromManager (int userId) throws SQLException {
        String query = "update users set isManager = false where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }
}
