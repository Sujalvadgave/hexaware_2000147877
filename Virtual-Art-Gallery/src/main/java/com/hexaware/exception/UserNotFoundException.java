package com.hexaware.exception;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException() {
        super("User not found in the database");
    }
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(int userId) {
        super("User with ID " + userId + " not found in the database");
    }
}