package com.museflow.backend.exhibition.controller;

import com.museflow.backend.exception.GlobalExceptionHandler;
import com.museflow.backend.exhibition.model.Exhibition;
import com.museflow.backend.exhibition.service.ExhibitionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExhibitionControllerTest {

    private MockMvc mockMvc;
    private ExhibitionService exhibitionService;

    @BeforeEach
    void setUp() {

        exhibitionService = mock(ExhibitionService.class);

        ExhibitionController controller =
                new ExhibitionController(exhibitionService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private Exhibition createTestExhibition() {

        return new Exhibition(
                "museum-123",
                "Future of Technology",
                "Interactive art and technology",
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2027, 1, 15),
                null
        );
    }

    // Test 1: POST valid exhibition → 201
    @Test
    void shouldCreateExhibition() throws Exception {

        Exhibition exhibition = createTestExhibition();

        when(exhibitionService.createExhibition(any(Exhibition.class)))
                .thenReturn(exhibition);

        mockMvc.perform(
                post("/api/v1/exhibitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "museumId": "museum-123",
                                  "title": "Future of Technology",
                                  "description": "Interactive art and technology",
                                  "startDate": "2026-10-10",
                                  "endDate": "2027-01-15"
                                }
                                """)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title")
                .value("Future of Technology"))
        .andExpect(jsonPath("$.museumId")
                .value("museum-123"));
    }

    // Test 2: POST missing required fields → 400
    @Test
    void shouldRejectInvalidExhibition() throws Exception {

        mockMvc.perform(
                post("/api/v1/exhibitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "museumId": "",
                                  "title": "",
                                  "startDate": null,
                                  "endDate": null
                                }
                                """)
        )
        .andExpect(status().isBadRequest());

        verify(exhibitionService, never())
                .createExhibition(any(Exhibition.class));
    }

    // Test 3: POST invalid dates → 400
    @Test
    void shouldRejectInvalidDates() throws Exception {

        when(exhibitionService.createExhibition(any(Exhibition.class)))
                .thenThrow(new IllegalArgumentException(
                        "End date cannot be before start date"
                ));

        mockMvc.perform(
                post("/api/v1/exhibitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "museumId": "museum-123",
                                  "title": "Invalid Exhibition",
                                  "startDate": "2026-12-20",
                                  "endDate": "2026-10-10"
                                }
                                """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message")
                .value("End date cannot be before start date"));
    }

    // Test 4: GET all exhibitions → 200
    @Test
    void shouldReturnAllExhibitions() throws Exception {

        when(exhibitionService.getAllExhibitions())
                .thenReturn(List.of(createTestExhibition()));

        mockMvc.perform(
                get("/api/v1/exhibitions")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title")
                .value("Future of Technology"));
    }

    // Test 5: GET existing exhibition → 200
    @Test
    void shouldReturnExhibitionById() throws Exception {

        when(exhibitionService.getExhibitionById("exhibition-123"))
                .thenReturn(Optional.of(createTestExhibition()));

        mockMvc.perform(
                get("/api/v1/exhibitions/exhibition-123")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title")
                .value("Future of Technology"));
    }

    // Test 6: GET nonexistent exhibition → 404
    @Test
    void shouldReturn404WhenExhibitionDoesNotExist() throws Exception {

        when(exhibitionService.getExhibitionById("missing-id"))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                get("/api/v1/exhibitions/missing-id")
        )
        .andExpect(status().isNotFound());
    }

    // Test 7: GET exhibitions by museum ID → 200
    @Test
    void shouldReturnExhibitionsByMuseumId() throws Exception {

        when(exhibitionService.getExhibitionsByMuseumId("museum-123"))
                .thenReturn(List.of(createTestExhibition()));

        mockMvc.perform(
                get("/api/v1/museums/museum-123/exhibitions")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].museumId")
                .value("museum-123"))
        .andExpect(jsonPath("$[0].title")
                .value("Future of Technology"));
    }
}