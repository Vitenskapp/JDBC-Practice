package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null) {
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connection successfully");
            } catch (SQLException sqlException) {
                throw new DbException("Error in connection: " + sqlException.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException sqlException) {
                throw new DbException(sqlException.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch(IOException ioException) {
            throw new DbException(ioException.getMessage());
        }
    }

    public static void closeStatement(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }



}
