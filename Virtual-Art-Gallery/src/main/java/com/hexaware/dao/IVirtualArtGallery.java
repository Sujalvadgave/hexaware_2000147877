package com.hexaware.dao;

import com.hexaware.entity.Artwork;
import com.hexaware.exception.ArtworkNotFoundException;
import com.hexaware.exception.UserNotFoundException;

import java.util.List;

public interface IVirtualArtGallery {

    // Artwork Management
    boolean addArtwork(Artwork artwork);

    boolean updateArtwork(Artwork artwork) throws ArtworkNotFoundException;

    boolean removeArtwork(int artworkId) throws ArtworkNotFoundException;

    Artwork getArtworkById(int artworkId) throws ArtworkNotFoundException;

    List<Artwork> searchArtworks(String keyword);

    // User Favorites
    boolean addArtworkToFavorite(int userId, int artworkId) throws UserNotFoundException, ArtworkNotFoundException;

    boolean removeArtworkFromFavorite(int userId, int artworkId) throws UserNotFoundException, ArtworkNotFoundException;

    List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException;
}
