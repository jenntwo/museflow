package com.museflow.backend.artwork.controller;

import com.museflow.backend.artwork.model.Artwork;
import com.museflow.backend.artwork.service.ArtworkService;
import com.museflow.backend.exception.GlobalExceptionHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArtworkControllerTest {

    private MockMvc mockMvc;
    private ArtworkService artworkService;

    @BeforeEach
    void setUp() {

        artworkService = mock(ArtworkService.class);

        ArtworkController controller = new ArtworkController(artworkService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
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

    // Test 1: Valid artwork → 201
    @Test
    void shouldCreateArtwork() throws Exception {

        when(artworkService.createArtwork(any(Artwork.class)))
                .thenReturn(createTestArtwork());

        mockMvc.perform(
                post("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "exhibitionId": "exhibition-123",
                                  "title": "The Starry Night",
                                  "artist": "Vincent van Gogh",
                                  "yearCreated": 1889,
                                  "medium": "Oil on canvas"
                                }
                                """)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title")
                .value("The Starry Night"))
        .andExpect(jsonPath("$.artist")
                .value("Vincent van Gogh"));
    }

    // Test 2: Missing required fields → 400
    @Test
    void shouldRejectInvalidArtwork() throws Exception {

        mockMvc.perform(
                post("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "exhibitionId": "",
                                  "title": ""
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(artworkService, never())
                .createArtwork(any(Artwork.class));
    }

    // Test 3: Exhibition does not exist → 400
    @Test
    void shouldRejectArtworkWhenExhibitionDoesNotExist()
            throws Exception {

        when(artworkService.createArtwork(any(Artwork.class)))
                .thenThrow(new IllegalArgumentException(
                        "Exhibition does not exist"
                ));

        mockMvc.perform(
                post("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "exhibitionId": "missing-exhibition",
                                  "title": "Test Artwork"
                                }
                                """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message")
                .value("Exhibition does not exist"));
    }

    // Test 4: Get all artworks → 200
    @Test
    void shouldReturnAllArtworks() throws Exception {

        when(artworkService.getAllArtworks())
                .thenReturn(List.of(createTestArtwork()));

        mockMvc.perform(
                get("/api/v1/artworks")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title")
                .value("The Starry Night"));
    }

    // Test 5: Get existing artwork → 200
    @Test
    void shouldReturnArtworkById() throws Exception {

        when(artworkService.getArtworkById("artwork-123"))
                .thenReturn(Optional.of(createTestArtwork()));

        mockMvc.perform(
                get("/api/v1/artworks/artwork-123")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.artist")
                .value("Vincent van Gogh"));
    }

    // Test 6: Get nonexistent artwork → 404
    @Test
    void shouldReturn404WhenArtworkDoesNotExist()
            throws Exception {

        when(artworkService.getArtworkById("missing-id"))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                get("/api/v1/artworks/missing-id")
        )
        .andExpect(status().isNotFound());
    }

    // Test 7: Get artworks by exhibition → 200
    @Test
    void shouldReturnArtworksByExhibitionId()
            throws Exception {

        when(artworkService.getArtworksByExhibitionId("exhibition-123"))
                .thenReturn(List.of(createTestArtwork()));

        mockMvc.perform(
                get("/api/v1/exhibitions/exhibition-123/artworks")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].exhibitionId")
                .value("exhibition-123"))
        .andExpect(jsonPath("$[0].title")
                .value("The Starry Night"));
    }
}