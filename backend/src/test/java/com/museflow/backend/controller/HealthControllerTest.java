package com.museflow.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HealthControllerTest {

    private final MockMvc mockMvc =
            MockMvcBuilders
                    .standaloneSetup(new HealthController())
                    .build();

    @Test
    void shouldReturnHealthyStatus() throws Exception {

        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(
                                MediaType.APPLICATION_JSON
                        )
                )
                .andExpect(
                        jsonPath("$.status").value("UP")
                )
                .andExpect(
                        jsonPath("$.service")
                                .value("MuseFlow Backend")
                );
    }
}