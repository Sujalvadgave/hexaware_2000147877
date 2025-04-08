package com.hexaware.main;

import com.hexaware.dao.IVirtualArtGallery;
import com.hexaware.dao.VirtualArtGalleryImpl;
import com.hexaware.entity.Artwork;
import com.hexaware.exception.ArtworkNotFoundException;
import com.hexaware.exception.UserNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    private static IVirtualArtGallery artGallery;
    private static Scanner scanner;
    private static SimpleDateFormat dateFormat;

    public static void main(String[] args) {
        // Initialize objects
        artGallery = new VirtualArtGalleryImpl();
        scanner = new Scanner(System.in);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        boolean exit = false;
        while (!exit) {
            // Display menu
            System.out.println("\n===== Virtual Art Gallery System =====");
            System.out.println("1. Add Artwork");
            System.out.println("2. Update Artwork");
            System.out.println("3. Remove Artwork");
            System.out.println("4. Get Artwork by ID");
            System.out.println("5. Search Artworks");
            System.out.println("6. Add Artwork to Favorites");
            System.out.println("7. Remove Artwork from Favorites");
            System.out.println("8. View User's Favorite Artworks");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addArtwork();
                        break;
                    case 2:
                        updateArtwork();
                        break;
                    case 3:
                        removeArtwork();
                        break;
                    case 4:
                        getArtworkById();
                        break;
                    case 5:
                        searchArtworks();
                        break;
                    case 6:
                        addArtworkToFavorite();
                        break;
                    case 7:
                        removeArtworkFromFavorite();
                        break;
                    case 8:
                        getUserFavoriteArtworks();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Thank you for using Virtual Art Gallery System!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void addArtwork() {
        try {
            System.out.println("\n--- Add New Artwork ---");

            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter creation date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();
            Date creationDate = dateFormat.parse(dateStr);

            System.out.print("Enter medium: ");
            String medium = scanner.nextLine();

            System.out.print("Enter image URL: ");
            String imageURL = scanner.nextLine();

            System.out.print("Enter artist ID: ");
            int artistId = Integer.parseInt(scanner.nextLine());

            Artwork artwork = new Artwork(0, title, description, creationDate, medium, imageURL, artistId);

            boolean success = artGallery.addArtwork(artwork);
            if (success) {
                System.out.println("Artwork added successfully!");
            } else {
                System.out.println("Failed to add artwork.");
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter a valid integer for artist ID.");
        } catch (Exception e) {
            System.out.println("Error adding artwork: " + e.getMessage());
        }
    }

    private static void updateArtwork() {
        try {
            System.out.println("\n--- Update Artwork ---");

            System.out.print("Enter artwork ID to update: ");
            int artworkId = Integer.parseInt(scanner.nextLine());

            try {
                // First get the current artwork
                Artwork existingArtwork = artGallery.getArtworkById(artworkId);

                System.out.println("Current artwork details:");
                System.out.println(existingArtwork);

                System.out.print("Enter new title (press Enter to keep current): ");
                String title = scanner.nextLine();
                if (!title.isEmpty()) {
                    existingArtwork.setTitle(title);
                }

                System.out.print("Enter new description (press Enter to keep current): ");
                String description = scanner.nextLine();
                if (!description.isEmpty()) {
                    existingArtwork.setDescription(description);
                }

                System.out.print("Enter new creation date (yyyy-MM-dd, press Enter to keep current): ");
                String dateStr = scanner.nextLine();
                if (!dateStr.isEmpty()) {
                    Date creationDate = dateFormat.parse(dateStr);
                    existingArtwork.setCreationDate(creationDate);
                }

                System.out.print("Enter new medium (press Enter to keep current): ");
                String medium = scanner.nextLine();
                if (!medium.isEmpty()) {
                    existingArtwork.setMedium(medium);
                }

                System.out.print("Enter new image URL (press Enter to keep current): ");
                String imageURL = scanner.nextLine();
                if (!imageURL.isEmpty()) {
                    existingArtwork.setImageURL(imageURL);
                }

                System.out.print("Enter new artist ID (press Enter to keep current): ");
                String artistIdStr = scanner.nextLine();
                if (!artistIdStr.isEmpty()) {
                    int artistId = Integer.parseInt(artistIdStr);
                    existingArtwork.setArtistId(artistId);
                }

                boolean success = artGallery.updateArtwork(existingArtwork);
                if (success) {
                    System.out.println("Artwork updated successfully!");
                } else {
                    System.out.println("Failed to update artwork.");
                }

            } catch (ArtworkNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter a valid integer.");
        } catch (Exception e) {
            System.out.println("Error updating artwork: " + e.getMessage());
        }
    }

    private static void removeArtwork() {
        try {
            System.out.println("\n--- Remove Artwork ---");

            System.out.print("Enter artwork ID to remove: ");
            int artworkId = Integer.parseInt(scanner.nextLine());

            System.out.print("Are you sure you want to remove this artwork? (y/n): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                try {
                    boolean success = artGallery.removeArtwork(artworkId);
                    if (success) {
                        System.out.println("Artwork removed successfully!");
                    } else {
                        System.out.println("Failed to remove artwork.");
                    }
                } catch (ArtworkNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Artwork removal cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter a valid integer for artwork ID.");
        } catch (Exception e) {
            System.out.println("Error removing artwork: " + e.getMessage());
        }
    }

    private static void getArtworkById() {
        try {
            System.out.println("\n--- Get Artwork by ID ---");

            System.out.print("Enter artwork ID: ");
            int artworkId = Integer.parseInt(scanner.nextLine());

            try {
                Artwork artwork = artGallery.getArtworkById(artworkId);
                System.out.println("\nArtwork Details:");
                System.out.println("ID: " + artwork.getArtworkId());
                System.out.println("Title: " + artwork.getTitle());
                System.out.println("Description: " + artwork.getDescription());
                System.out.println("Creation Date: " + dateFormat.format(artwork.getCreationDate()));
                System.out.println("Medium: " + artwork.getMedium());
                System.out.println("Image URL: " + artwork.getImageURL());
                System.out.println("Artist ID: " + artwork.getArtistId());
            } catch (ArtworkNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter a valid integer for artwork ID.");
        } catch (Exception e) {
            System.out.println("Error retrieving artwork: " + e.getMessage());
        }
    }

    private static void searchArtworks() {
        try {
            System.out.println("\n--- Search Artworks ---");

            System.out.print("Enter search keyword: ");
            String keyword = scanner.nextLine();

            List<Artwork> artworks = artGallery.searchArtworks(keyword);

            if (artworks.isEmpty()) {
                System.out.println("No artworks found matching your search criteria.");
            } else {
                System.out.println("\nSearch Results:");
                for (Artwork artwork : artworks) {
                    System.out.println("---------------------------------------");
                    System.out.println("ID: " + artwork.getArtworkId());
                    System.out.println("Title: " + artwork.getTitle());
                    System.out.println("Description: " + artwork.getDescription());
                    System.out.println("Creation Date: " + dateFormat.format(artwork.getCreationDate()));
                    System.out.println("Medium: " + artwork.getMedium());
                }
                System.out.println("---------------------------------------");
                System.out.println("Total artworks found: " + artworks.size());
            }

        } catch (Exception e) {
            System.out.println("Error searching artworks: " + e.getMessage());
        }
    }

    private static void addArtworkToFavorite() {
        try {
            System.out.println("\n--- Add Artwork to Favorites ---");

            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter artwork ID: ");
            int artworkId = Integer.parseInt(scanner.nextLine());

            try {
                boolean success = artGallery.addArtworkToFavorite(userId, artworkId);
                if (success) {
                    System.out.println("Artwork added to favorites successfully!");
                } else {
                    System.out.println("Failed to add artwork to favorites.");
                }
            } catch (UserNotFoundException | ArtworkNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter valid integers for user ID and artwork ID.");
        } catch (Exception e) {
            System.out.println("Error adding artwork to favorites: " + e.getMessage());
        }
    }

    private static void removeArtworkFromFavorite() {
        try {
            System.out.println("\n--- Remove Artwork from Favorites ---");

            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter artwork ID: ");
            int artworkId = Integer.parseInt(scanner.nextLine());

            try {
                boolean success = artGallery.removeArtworkFromFavorite(userId, artworkId);
                if (success) {
                    System.out.println("Artwork removed from favorites successfully!");
                } else {
                    System.out.println("Failed to remove artwork from favorites.");
                }
            } catch (UserNotFoundException | ArtworkNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter valid integers for user ID and artwork ID.");
        } catch (Exception e) {
            System.out.println("Error removing artwork from favorites: " + e.getMessage());
        }
    }

    private static void getUserFavoriteArtworks() {
        try {
            System.out.println("\n--- View User's Favorite Artworks ---");

            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine());

            try {
                List<Artwork> favorites = artGallery.getUserFavoriteArtworks(userId);

                if (favorites.isEmpty()) {
                    System.out.println("This user has no favorite artworks.");
                } else {
                    System.out.println("\nUser's Favorite Artworks:");
                    for (Artwork artwork : favorites) {
                        System.out.println("---------------------------------------");
                        System.out.println("ID: " + artwork.getArtworkId());
                        System.out.println("Title: " + artwork.getTitle());
                        System.out.println("Description: " + artwork.getDescription());
                        System.out.println("Creation Date: " + dateFormat.format(artwork.getCreationDate()));
                        System.out.println("Medium: " + artwork.getMedium());
                    }
                    System.out.println("---------------------------------------");
                    System.out.println("Total favorite artworks: " + favorites.size());
                }

            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Please enter a valid integer for user ID.");
        } catch (Exception e) {
            System.out.println("Error retrieving favorite artworks: " + e.getMessage());
        }
    }
}