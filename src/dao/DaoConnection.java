package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoConnection {
    final static String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/arcane_game";
    final static String USER = "postgres";
    final static String PASSWORD = "1234";

    public static Connection  getConnection() throws SQLException {
        return  DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
    }
}
