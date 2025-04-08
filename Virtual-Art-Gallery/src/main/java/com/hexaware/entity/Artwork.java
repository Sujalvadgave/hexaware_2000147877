package com.hexaware.entity;

import java.util.Date;

public class Artwork {
    private int artworkId;
    private String title;
    private String description;
    private Date creationDate;
    private String medium;
    private String imageURL;
    private int artistId;
    
    // Default constructor
    public Artwork() {
    }
    
    // Parameterized constructor
    public Artwork(int artworkId, String title, String description, Date creationDate, 
                   String medium, String imageURL, int artistId) {
        this.artworkId = artworkId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.medium = medium;
        this.imageURL = imageURL;
        this.artistId = artistId;
    }

    public Artwork(int i, String starryNight, String s, String s1, String oilOnCanvas, String urlToImage) {
    }

    // Getters and Setters
    public int getArtworkId() {
        return artworkId;
    }
    
    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getMedium() {
        return medium;
    }
    
    public void setMedium(String medium) {
        this.medium = medium;
    }
    
    public String getImageURL() {
        return imageURL;
    }
    
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    
    public int getArtistId() {
        return artistId;
    }
    
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
    
    @Override
    public String toString() {
        return "Artwork{" +
                "artworkId=" + artworkId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", medium='" + medium + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}