package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Database connection details (update these with your actual credentials)
    private static final String URL = "jdbc:mysql://localhost:3306/HMBanks";
    private static final String USER = "root";       // Replace with your MySQL username
    private static final String PASSWORD = "Hexaware@12345"; // Replace with your MySQL password

    /**
     * Establishes and returns a connection to the database.
     * @return A Connection object to the HMBank database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getDBConn() throws SQLException {
        try {
            // Optional: Explicitly load the MySQL driver (not strictly needed for modern JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}