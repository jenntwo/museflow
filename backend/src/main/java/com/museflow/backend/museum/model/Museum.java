package com.museflow.backend.museum.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "museums")
public class Museum {

    @Id
    private String id;

    private String name;

    private String description;

    private String city;

    private String country;

    private String imageUrl;

    public Museum() {
    }

    public Museum(
            String name,
            String description,
            String city,
            String country,
            String imageUrl
    ) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}