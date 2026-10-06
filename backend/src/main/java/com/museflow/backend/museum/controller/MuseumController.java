package com.museflow.backend.museum.controller;

import com.museflow.backend.museum.model.Museum;
import com.museflow.backend.museum.service.MuseumService;
import org.springframework.http.ResponseEntity;
import com.museflow.backend.museum.dto.CreateMuseumRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/museums")
public class MuseumController {

    private final MuseumService museumService;

    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @PostMapping
    public ResponseEntity<Museum> createMuseum(
            @Valid @RequestBody CreateMuseumRequest request) {

        Museum museum = new Museum(
                request.getName(),
                request.getDescription(),
                request.getCity(),
                request.getCountry(),
                request.getImageUrl());

        Museum createdMuseum = museumService.createMuseum(museum);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMuseum);
    }

    @GetMapping
    public ResponseEntity<List<Museum>> getAllMuseums() {
        return ResponseEntity.ok(
                museumService.getAllMuseums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Museum> getMuseumById(
            @PathVariable String id) {
        return museumService
                .getMuseumById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build());
    }
}