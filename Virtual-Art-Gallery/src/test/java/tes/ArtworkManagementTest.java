package tes;

import com.hexaware.dao.IVirtualArtGallery;
import com.hexaware.dao.VirtualArtGalleryImpl;
import com.hexaware.entity.Artwork;
import com.hexaware.exception.ArtworkNotFoundException;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtworkManagementTest {

    private IVirtualArtGallery artGallery;
    private Artwork testArtwork;

    @BeforeEach
    public void setUp() {
        artGallery = new VirtualArtGalleryImpl();

        // Create a test artwork
        testArtwork = new Artwork();
        testArtwork.setArtworkId(3); // Use a unique ID for testing
        testArtwork.setTitle("Test Artwork");
        testArtwork.setDescription("This is a test artwork.");
        testArtwork.setCreationDate(new Date());
        testArtwork.setMedium("Oil on Canvas");
        testArtwork.setImageURL("http://example.com/test.jpg");
        testArtwork.setArtistId(3); // Assuming artist ID 1 exists
    }

    @Test
    public void testAddArtwork() {
        boolean result = artGallery.addArtwork(testArtwork);
        assertTrue(result, "Artwork should be added successfully.");

        // Verify that the artwork was added
        try {
            Artwork addedArtwork = artGallery.getArtworkById(testArtwork.getArtworkId());
            assertNotNull(addedArtwork, "The added artwork should not be null.");
            assertEquals(testArtwork.getTitle(), addedArtwork.getTitle(), "The title of the added artwork should match.");
        } catch (ArtworkNotFoundException e) {
            fail("Artwork should exist after adding: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateArtwork() throws ArtworkNotFoundException {
        artGallery.addArtwork(testArtwork);

        // Update the artwork
        testArtwork.setTitle("Updated Artwork");
        boolean result = artGallery.updateArtwork(testArtwork);
        assertTrue(result, "Artwork should be updated successfully.");

        // Verify that the artwork was updated
        Artwork updatedArtwork = artGallery.getArtworkById(testArtwork.getArtworkId());
        assertEquals("Updated Artwork", updatedArtwork.getTitle(), "The artwork title should be updated.");
    }

    @Test
    public void testRemoveArtwork() {
        // First, add the artwork to ensure it exists
        artGallery.addArtwork(testArtwork);

        // Now, remove the artwork
        boolean removeResult = false;
        try {
            removeResult = artGallery.removeArtwork(testArtwork.getArtworkId());
        } catch (ArtworkNotFoundException e) {
            fail("Artwork should exist and be removable.");
        }
        assertTrue(removeResult, "Artwork should be removed successfully.");

        // Verify that the artwork was removed
        assertThrows(ArtworkNotFoundException.class, () -> {
            artGallery.getArtworkById(testArtwork.getArtworkId());
        }, "Artwork should not be found after removal.");
    }

    @Test
    public void testGetArtworkById() throws ArtworkNotFoundException {
        artGallery.addArtwork(testArtwork);
        Artwork retrievedArtwork = artGallery.getArtworkById(testArtwork.getArtworkId());
        assertEquals(testArtwork.getTitle(), retrievedArtwork.getTitle(), "The retrieved artwork title should match the added artwork title.");
    }



    @AfterEach
    public void tearDown() {
        // Clean up: Attempt to remove the artwork if it still exists
        try {
            artGallery.removeArtwork(testArtwork.getArtworkId());
        } catch (ArtworkNotFoundException e) {
            // Ignore if the artwork is not found
        }
    }
}