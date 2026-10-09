package com.museflow.backend.exhibition.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "exhibitions")
public class Exhibition {

    @Id
    private String id;

    private String museumId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;

    public Exhibition() {
    }

    public Exhibition(
            String museumId,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            String imageUrl) {
        this.museumId = museumId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

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