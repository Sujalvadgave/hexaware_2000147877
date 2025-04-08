package com.hexaware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    private static Connection connection;
    
    public static Connection getConnection(String connectionString) {
        if (connection == null) {
            try {
                String[] params = connectionString.split(";");
                String driver = params[0].split("=")[1];
                String url = params[1].split("=")[1];
                String username = params[2].split("=")[1];
                String password = params[3].split("=")[1];
                
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
                
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error establishing database connection: " + e.getMessage());
            }
        }
        
        return connection;
    }
}