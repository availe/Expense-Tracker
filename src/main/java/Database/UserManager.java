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
                        resultSet.getString("createdAt"),
                        resultSet.getString("lastLogin"),
                        resultSet.getBoolean("isManager"),
                        resultSet.getBoolean("isRoot"),
                        resultSet.getBoolean("hasApproval"),
                        resultSet.getString("passwordHash")));
            }
        }
        return users;
    }

    public void addUser(UserRecord newUser) throws SQLException {
        String query = "insert into users (firstName, lastName, email, passwordHash, isManager, isRoot, lastLogin) values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newUser.getFirstName());
            preparedStatement.setString(2, newUser.getLastName());
            preparedStatement.setString(3, newUser.getEmail());
            preparedStatement.setString(4, newUser.getPassHash());
            preparedStatement.setBoolean(5, newUser.getIsManager());
            preparedStatement.setBoolean(6, newUser.getIsRoot());
            preparedStatement.setString(7, newUser.getLastLogin());
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

    // we use 1 and 0 instead of true and false since sqlite doesn't support booleans, so we're using integers instead
    public void upgradeUserToManager(int userId) throws SQLException {
        String query = "update users set isManager = 1 where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void downgradeUserFromManager (int userId) throws SQLException {
        String query = "update users set isManager = 0 where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public UserRecord authenticateUser (String email, String password) throws SQLException {
        String query = "select * from users where email = ? and passwordHash = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            // don't let user login if they haven't been approved yet
            if (resultSet.getBoolean("hasApproval") == false) return null;

            if (resultSet.next()) {
                return new UserRecord(
                        resultSet.getInt("userId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("createdAt"),
                        resultSet.getString("lastLogin"),
                        resultSet.getBoolean("isManager"),
                        resultSet.getBoolean("isRoot"),
                        resultSet.getBoolean("hasApproval"),
                        resultSet.getString("passwordHash"));
            } else {
                return null;
            }
        }
    }

    public void approveUser(int userId) {
        String query = "update users set hasApproval = 1 where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(int userId, String password) {
        String query = "update users set passwordHash = ? where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLastLogin(int userId) {
        String query = "update users set lastLogin = CURRENT_TIMESTAMP where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
