package com.hexaware.entity;

public class UserFavoriteArtwork {
    private int userId;
    private int artworkId;
    
    // Default constructor
    public UserFavoriteArtwork() {
    }
    
    // Parameterized constructor
    public UserFavoriteArtwork(int userId, int artworkId) {
        this.userId = userId;
        this.artworkId = artworkId;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getArtworkId() {
        return artworkId;
    }
    
    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }
    
    @Override
    public String toString() {
        return "UserFavoriteArtwork{" +
                "userId=" + userId +
                ", artworkId=" + artworkId +
                '}';
    }
}