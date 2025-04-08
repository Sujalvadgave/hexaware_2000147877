package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    
    public static String getPropertyString(String fileName) {
        Properties properties = new Properties();
        String connectionString = null;
        
        try (FileInputStream fis = new FileInputStream("src/main/resources/" + fileName)) {
            properties.load(fis);
            
            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            
            connectionString = "driver=" + driver + ";url=" + url + ";username=" + username + ";password=" + password;
            
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
        }
        
        return connectionString;
    }
}