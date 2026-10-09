package com.museflow.backend.artwork.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "artworks")
public class Artwork {

    @Id
    private String id;

    private String exhibitionId;
    private String title;
    private String artist;
    private Integer yearCreated;
    private String medium;
    private String description;
    private String imageUrl;

    public Artwork() {
    }

    public Artwork(
            String exhibitionId,
            String title,
            String artist,
            Integer yearCreated,
            String medium,
            String description,
            String imageUrl
    ) {
        this.exhibitionId = exhibitionId;
        this.title = title;
        this.artist = artist;
        this.yearCreated = yearCreated;
        this.medium = medium;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

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