
package com.museflow.backend.exception;

import com.museflow.backend.artwork.controller.ArtworkController;
import com.museflow.backend.artwork.service.ArtworkService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class GlobalExceptionHandlerTest {

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

    @Test
    void shouldReturn404WhenArtworkDoesNotExist() throws Exception {

        when(artworkService.getArtworkById("missing-id"))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                get("/api/v1/artworks/missing-id")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("Artwork not found: missing-id"))
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/artworks/missing-id"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WhenArtworkIsInvalid() throws Exception {

        String invalidArtwork = """
                {
                    "exhibitionId": "exhibition-123",
                    "title": "",
                    "artist": "",
                    "yearCreated": -1889,
                    "medium": "Oil on canvas",
                    "description": "Test artwork"
                }
                """;

        mockMvc.perform(
                post("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidArtwork))

                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/artworks"))
                .andExpect(jsonPath("$.timestamp").exists());

        verifyNoInteractions(artworkService);
    }
}
