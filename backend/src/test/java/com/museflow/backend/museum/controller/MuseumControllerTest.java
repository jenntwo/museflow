package com.museflow.backend.museum.controller;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.museflow.backend.museum.model.Museum;
import com.museflow.backend.museum.service.MuseumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MuseumControllerTest {

    private MockMvc mockMvc;
    private MuseumService museumService;

    @BeforeEach
    void setUp() {

        museumService = mock(MuseumService.class);

        MuseumController museumController = new MuseumController(museumService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(museumController)
                .build();
    }

    @Test
    void shouldCreateMuseum() throws Exception {

        Museum createdMuseum = new Museum(
                "Exploratorium",
                "Museum of science, technology and arts",
                "San Francisco",
                "United States",
                "https://example.com/exploratorium.jpg");

        when(museumService.createMuseum(any(Museum.class)))
                .thenReturn(createdMuseum);

        mockMvc.perform(
                post("/api/v1/museums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Exploratorium",
                                  "description": "Museum of science, technology and arts",
                                  "city": "San Francisco",
                                  "country": "United States",
                                  "imageUrl": "https://example.com/exploratorium.jpg"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Exploratorium"))
                .andExpect(jsonPath("$.city")
                        .value("San Francisco"));
    }

    @Test
    void shouldRejectInvalidMuseum() throws Exception {

        mockMvc.perform(
                post("/api/v1/museums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "description": "Invalid museum",
                                  "city": "",
                                  "country": "United States"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAllMuseums() throws Exception {

        Museum museum = new Museum(
                "Exploratorium",
                "Museum of science, technology and arts",
                "San Francisco",
                "United States",
                "https://example.com/exploratorium.jpg");

        when(museumService.getAllMuseums())
                .thenReturn(List.of(museum));

        mockMvc.perform(
                get("/api/v1/museums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Exploratorium"))
                .andExpect(jsonPath("$[0].city")
                        .value("San Francisco"));
    }

    @Test
    void shouldReturnMuseumById() throws Exception {

        Museum museum = new Museum(
                "Exploratorium",
                "Museum of science, technology and arts",
                "San Francisco",
                "United States",
                "https://example.com/exploratorium.jpg");

        when(museumService.getMuseumById("museum-123"))
                .thenReturn(Optional.of(museum));

        mockMvc.perform(
                get("/api/v1/museums/museum-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Exploratorium"))
                .andExpect(jsonPath("$.country")
                        .value("United States"));
    }

@Test
void shouldReturn404WhenMuseumDoesNotExist() throws Exception {

    when(museumService.getMuseumById("missing-id"))
            .thenReturn(Optional.empty());

    mockMvc.perform(
                    get("/api/v1/museums/missing-id")
            )
            .andExpect(status().isNotFound());
}

}