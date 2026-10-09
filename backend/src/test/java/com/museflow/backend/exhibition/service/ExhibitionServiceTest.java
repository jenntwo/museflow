package com.museflow.backend.exhibition.service;

import com.museflow.backend.exhibition.model.Exhibition;
import com.museflow.backend.exhibition.repository.ExhibitionRepository;
import com.museflow.backend.museum.repository.MuseumRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExhibitionServiceTest {

    private ExhibitionService exhibitionService;
    private ExhibitionRepository exhibitionRepository;
    private MuseumRepository museumRepository;

    @BeforeEach
    void setUp() {
        exhibitionRepository = mock(ExhibitionRepository.class);
        museumRepository = mock(MuseumRepository.class);

        exhibitionService = new ExhibitionService(
                exhibitionRepository,
                museumRepository);
    }

    private Exhibition createValidExhibition() {
        return new Exhibition(
                "museum-123",
                "Future of Technology",
                "Interactive art and technology",
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2027, 1, 15),
                null);
    }

    @Test
    void shouldCreateExhibitionWhenValid() {

        Exhibition exhibition = createValidExhibition();

        when(museumRepository.existsById("museum-123"))
                .thenReturn(true);

        when(exhibitionRepository.save(any(Exhibition.class)))
                .thenReturn(exhibition);

        Exhibition result = exhibitionService.createExhibition(exhibition);

        assertEquals("Future of Technology", result.getTitle());

        verify(exhibitionRepository).save(exhibition);
    }

    @Test
    void shouldRejectExhibitionWhenMuseumDoesNotExist() {

        Exhibition exhibition = createValidExhibition();

        when(museumRepository.existsById("museum-123"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exhibitionService.createExhibition(exhibition));

        assertEquals(
                "Museum does not exist",
                exception.getMessage());

        verify(exhibitionRepository, never()).save(any());
    }

    @Test
    void shouldRejectExhibitionWhenEndDateIsBeforeStartDate() {

        Exhibition exhibition = new Exhibition(
                "museum-123",
                "Invalid Exhibition",
                "Invalid dates",
                LocalDate.of(2026, 12, 20),
                LocalDate.of(2026, 10, 10),
                null);

        when(museumRepository.existsById("museum-123"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exhibitionService.createExhibition(exhibition));

        assertEquals(
                "End date cannot be before start date",
                exception.getMessage());

        verify(exhibitionRepository, never()).save(any());
    }
}