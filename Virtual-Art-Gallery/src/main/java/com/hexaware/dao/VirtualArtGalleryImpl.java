package com.hexaware.dao;

import com.hexaware.entity.Artwork;
import com.hexaware.exception.ArtworkNotFoundException;
import com.hexaware.exception.UserNotFoundException;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualArtGalleryImpl implements IVirtualArtGallery {

    private static Connection connection;

    public VirtualArtGalleryImpl() {
        String connectionString = DBPropertyUtil.getPropertyString("db.properties");
        connection = DBConnUtil.getConnection(connectionString);
    }

    @Override
    public boolean addArtwork(Artwork artwork) {
        String sql = "INSERT INTO Artwork (Title, Description, CreationDate, Medium, ImageURL, ArtistId) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, artwork.getTitle());
            ps.setString(2, artwork.getDescription());
            ps.setDate(3, new java.sql.Date(artwork.getCreationDate().getTime()));
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getImageURL());
            ps.setInt(6, artwork.getArtistId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding artwork: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateArtwork(Artwork artwork) throws ArtworkNotFoundException {
        // First check if the artwork exists
        if (!artworkExists(artwork.getArtworkId())) {
            throw new ArtworkNotFoundException(artwork.getArtworkId());
        }

        String sql = "UPDATE Artwork SET Title = ?, Description = ?, CreationDate = ?, Medium = ?, ImageURL = ?, ArtistId = ? WHERE ArtworkID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, artwork.getTitle());
            ps.setString(2, artwork.getDescription());
            ps.setDate(3, new java.sql.Date(artwork.getCreationDate().getTime()));
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getImageURL());
            ps.setInt(6, artwork.getArtistId());
            ps.setInt(7, artwork.getArtworkId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating artwork: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeArtwork(int artworkId) throws ArtworkNotFoundException {
        // First check if the artwork exists
        if (!artworkExists(artworkId)) {
            throw new ArtworkNotFoundException(artworkId);
        }

        String sql = "DELETE FROM Artwork WHERE ArtworkID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artworkId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error removing artwork: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Artwork getArtworkById(int artworkId) throws ArtworkNotFoundException {
        String sql = "SELECT * FROM Artwork WHERE ArtworkID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artworkId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setArtworkId(rs.getInt("ArtworkID"));
                    artwork.setTitle(rs.getString("Title"));
                    artwork.setDescription(rs.getString("Description"));
                    artwork.setCreationDate(rs.getDate("CreationDate"));
                    artwork.setMedium(rs.getString("Medium"));
                    artwork.setImageURL(rs.getString("ImageURL"));
                    artwork.setArtistId(rs.getInt("ArtistId"));
                    return artwork;
                } else {
                    throw new ArtworkNotFoundException(artworkId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving artwork: " + e.getMessage());
            throw new ArtworkNotFoundException("Error retrieving artwork with ID " + artworkId);
        }
    }

    @Override
    public List<Artwork> searchArtworks(String keyword) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM Artwork WHERE Title LIKE ? OR Description LIKE ? OR Medium LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setArtworkId(rs.getInt("ArtworkID"));
                    artwork.setTitle(rs.getString("Title"));
                    artwork.setDescription(rs.getString("Description"));
                    artwork.setCreationDate(rs.getDate("CreationDate"));
                    artwork.setMedium(rs.getString("Medium"));
                    artwork.setImageURL(rs.getString("ImageURL"));
                    artwork.setArtistId(rs.getInt("ArtistId"));
                    artworks.add(artwork);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching artworks: " + e.getMessage());
        }

        return artworks;
    }

    @Override
    public boolean addArtworkToFavorite(int userId, int artworkId) throws UserNotFoundException, ArtworkNotFoundException {
        // Check if user exists
        if (!userExists(userId)) {
            throw new UserNotFoundException(userId);
        }

        // Check if artwork exists
        if (!artworkExists(artworkId)) {
            throw new ArtworkNotFoundException(artworkId);
        }

        String sql = "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, artworkId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding artwork to favorites: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeArtworkFromFavorite(int userId, int artworkId) throws UserNotFoundException, ArtworkNotFoundException {
        // Check if user exists
        if (!userExists(userId)) {
            throw new UserNotFoundException(userId);
        }

        // Check if artwork exists
        if (!artworkExists(artworkId)) {
            throw new ArtworkNotFoundException(artworkId);
        }

        String sql = "DELETE FROM User_Favorite_Artwork WHERE UserID = ? AND ArtworkID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, artworkId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error removing artwork from favorites: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException {
        // Check if user exists
        if (!userExists(userId)) {
            throw new UserNotFoundException(userId);
        }

        List<Artwork> favorites = new ArrayList<>();
        String sql = "SELECT a.* FROM Artwork a " +
                "JOIN User_Favorite_Artwork ufa ON a.ArtworkID = ufa.ArtworkID " +
                "WHERE ufa.UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setArtworkId(rs.getInt("ArtworkID"));
                    artwork.setTitle(rs.getString("Title"));
                    artwork.setDescription(rs.getString("Description"));
                    artwork.setCreationDate(rs.getDate("CreationDate"));
                    artwork.setMedium(rs.getString("Medium"));
                    artwork.setImageURL(rs.getString("ImageURL"));
                    artwork.setArtistId(rs.getInt("ArtistId"));
                    favorites.add(artwork);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving favorite artworks: " + e.getMessage());
        }

        return favorites;
    }

    // Helper methods
    private boolean artworkExists(int artworkId) {
        String sql = "SELECT COUNT(*) FROM Artwork WHERE ArtworkID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, artworkId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking if artwork exists: " + e.getMessage());
        }

        return false;
    }

    private boolean userExists(int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking if user exists: " + e.getMessage());
        }

        return false;
    }
}
