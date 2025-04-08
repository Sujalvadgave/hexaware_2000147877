package com.hexaware.exception;

public class ArtworkNotFoundException extends Exception {
    
    public ArtworkNotFoundException() {
        super("Artwork not found in the database");
    }
    
    public ArtworkNotFoundException(String message) {
        super(message);
    }
    
    public ArtworkNotFoundException(int artworkId) {
        super("Artwork with ID " + artworkId + " not found in the database");
    }
}