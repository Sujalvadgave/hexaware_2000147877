package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    // Method to get connection string from property file
    public static String getPropertyString(String propertyFileName) {
        Properties properties = new Properties();
        String connectionString = "";

        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + propertyFileName);
                return null;
            }

            // Load the properties file
            properties.load(input);

            // Create connection string
            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Format: driver,url,username,password
            connectionString = driver + "," + url + "," + username + "," + password;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return connectionString;
    }
}