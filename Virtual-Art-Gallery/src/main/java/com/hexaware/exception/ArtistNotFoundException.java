package com.hexaware.exception;

public class ArtistNotFoundException extends Exception {
    private int artistId;

    public ArtistNotFoundException(int artistId) {
        this.artistId = artistId;
    }

    public ArtistNotFoundException(String message) {
        super(message);
    }

    public ArtistNotFoundException(String message, int artistId) {
        super(message);
        this.artistId = artistId;
    }

    public int getArtistId() {
        return artistId;
    }
}