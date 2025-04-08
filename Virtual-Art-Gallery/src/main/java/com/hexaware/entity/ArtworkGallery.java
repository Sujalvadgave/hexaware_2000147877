package com.hexaware.entity;

public class ArtworkGallery {
    private int artworkId;
    private int galleryId;
    
    // Default constructor
    public ArtworkGallery() {
    }
    
    // Parameterized constructor
    public ArtworkGallery(int artworkId, int galleryId) {
        this.artworkId = artworkId;
        this.galleryId = galleryId;
    }
    
    // Getters and Setters
    public int getArtworkId() {
        return artworkId;
    }
    
    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }
    
    public int getGalleryId() {
        return galleryId;
    }
    
    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }
    
    @Override
    public String toString() {
        return "ArtworkGallery{" +
                "artworkId=" + artworkId +
                ", galleryId=" + galleryId +
                '}';
    }
}