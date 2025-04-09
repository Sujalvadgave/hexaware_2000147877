package com.hexaware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    // Method to get connection object from connection string
    public static Connection getConnection(String connectionString) throws SQLException {
        Connection connection = null;

        try {
            // Split the connection string
            String[] parts = connectionString.split(",");

            if (parts.length != 4) {
                throw new SQLException("Invalid connection string format");
            }

            String driver = parts[0];
            String url = parts[1];
            String username = parts[2];
            String password = parts[3];

            // Register driver
            Class.forName(driver);

            // Create connection
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }

        return connection;
    }
}