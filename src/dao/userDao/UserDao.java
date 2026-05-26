package dao.userDao;

import abst.daoAbst.user.IUserDaoWriteable;
import abst.daoAbst.user.IUserDaoReadable;
import dao.DaoConnection;
import model.user.User;

import java.sql.*;

public class UserDao implements
        IUserDaoWriteable,
        IUserDaoReadable {

    private static UserDao instance;

    public static UserDao getInstance() {

        if (instance == null) {
            instance = new UserDao();
        }

        return instance;
    }

    private UserDao() {
    }

    @Override
    public void save(User user) {

        String sql = """
                INSERT INTO users (username, password, created_at)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            System.out.println("SAVE METHOD STARTED");

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setTimestamp(3, Timestamp.valueOf(user.getCreatedAt()));

            int affectedRows = statement.executeUpdate();

            System.out.println("Affected Rows: " + affectedRows);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("User could not be saved. Reason: " + e.getMessage(), e);
        }
    }


    @Override
    public User findByUsername(String username) {
        int counter = 1;
        String sql = """
                SELECT id, username, password, created_at
                FROM users
                WHERE username = ?
                """;
        try (Connection connection = DaoConnection.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(counter, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        null
                );
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Username check failed. Reason: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = DaoConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Username check failed. Reason: " + e.getMessage(), e);
        }

    }
}
