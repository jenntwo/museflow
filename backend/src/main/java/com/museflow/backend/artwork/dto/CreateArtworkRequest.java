package com.museflow.backend.artwork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateArtworkRequest {

    @NotBlank(message = "Exhibition ID is required")
    private String exhibitionId;

    @NotBlank(message = "Artwork title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 150, message = "Artist name must not exceed 150 characters")
    private String artist;

    private Integer yearCreated;

    @Size(max = 200, message = "Medium must not exceed 200 characters")
    private String medium;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    private String imageUrl;

    public String getExhibitionId() {
        return exhibitionId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getYearCreated() {
        return yearCreated;
    }

    public String getMedium() {
        return medium;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setExhibitionId(String exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}