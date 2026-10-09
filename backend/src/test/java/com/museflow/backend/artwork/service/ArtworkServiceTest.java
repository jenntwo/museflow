package com.museflow.backend.artwork.service;

import com.museflow.backend.artwork.model.Artwork;
import com.museflow.backend.artwork.repository.ArtworkRepository;
import com.museflow.backend.exhibition.repository.ExhibitionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArtworkServiceTest {

    private ArtworkService artworkService;
    private ArtworkRepository artworkRepository;
    private ExhibitionRepository exhibitionRepository;

    @BeforeEach
    void setUp() {
        artworkRepository = mock(ArtworkRepository.class);
        exhibitionRepository = mock(ExhibitionRepository.class);

        artworkService = new ArtworkService(
                artworkRepository,
                exhibitionRepository);
    }

    private Artwork createTestArtwork() {
        return new Artwork(
                "exhibition-123",
                "The Starry Night",
                "Vincent van Gogh",
                1889,
                "Oil on canvas",
                "A swirling night sky over a village.",
                null);
    }

    // Test 1: Valid artwork should be saved
    @Test
    void shouldCreateArtworkWhenExhibitionExists() {

        Artwork artwork = createTestArtwork();

        when(exhibitionRepository.existsById("exhibition-123"))
                .thenReturn(true);

        when(artworkRepository.save(any(Artwork.class)))
                .thenReturn(artwork);

        Artwork result = artworkService.createArtwork(artwork);

        assertEquals("The Starry Night", result.getTitle());
        assertEquals("Vincent van Gogh", result.getArtist());

        verify(artworkRepository).save(artwork);
    }

    // Test 2: Invalid exhibition should be rejected
    @Test
    void shouldRejectArtworkWhenExhibitionDoesNotExist() {

        Artwork artwork = createTestArtwork();

        when(exhibitionRepository.existsById("exhibition-123"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> artworkService.createArtwork(artwork));

        assertEquals(
                "Exhibition does not exist",
                exception.getMessage());

        verify(artworkRepository, never()).save(any());
    }

    // Test 3: Query artworks by exhibition ID
    @Test
    void shouldReturnArtworksByExhibitionId() {

        Artwork artwork = createTestArtwork();

        when(artworkRepository.findByExhibitionId("exhibition-123"))
                .thenReturn(List.of(artwork));

        List<Artwork> results = artworkService.getArtworksByExhibitionId("exhibition-123");

        assertEquals(1, results.size());
        assertEquals("The Starry Night", results.get(0).getTitle());

        verify(artworkRepository)
                .findByExhibitionId("exhibition-123");
    }
}