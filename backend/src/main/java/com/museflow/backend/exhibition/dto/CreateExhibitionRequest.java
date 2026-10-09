package com.museflow.backend.exhibition.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateExhibitionRequest {

    @NotBlank(message = "Museum ID is required")
    private String museumId;

    @NotBlank(message = "Exhibition title is required")
    @Size(max = 150, message = "Exhibition title must not exceed 150 characters")
    private String title;

    @Size(max = 1500, message = "Description must not exceed 1500 characters")
    private String description;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private String imageUrl;

    public String getMuseumId() {
        return museumId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}