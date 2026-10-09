package com.museflow.backend.exhibition.controller;

import com.museflow.backend.exhibition.dto.CreateExhibitionRequest;
import com.museflow.backend.exhibition.model.Exhibition;
import com.museflow.backend.exhibition.service.ExhibitionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    // Create Exhibition
    @PostMapping("/exhibitions")
    public ResponseEntity<Exhibition> createExhibition(
            @Valid @RequestBody CreateExhibitionRequest request) {

        Exhibition exhibition = new Exhibition(
                request.getMuseumId(),
                request.getTitle(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                request.getImageUrl());

        Exhibition createdExhibition = exhibitionService.createExhibition(exhibition);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdExhibition);
    }

    // Get All Exhibitions
    @GetMapping("/exhibitions")
    public ResponseEntity<List<Exhibition>> getAllExhibitions() {

        return ResponseEntity.ok(
                exhibitionService.getAllExhibitions());
    }

    // Get Exhibition By ID
    @GetMapping("/exhibitions/{id}")
    public ResponseEntity<Exhibition> getExhibitionById(
            @PathVariable String id) {

        return exhibitionService
                .getExhibitionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build());
    }

    // Get Exhibitions By Museum ID
    @GetMapping("/museums/{museumId}/exhibitions")
    public ResponseEntity<List<Exhibition>> getExhibitionsByMuseumId(
            @PathVariable String museumId) {

        return ResponseEntity.ok(
                exhibitionService.getExhibitionsByMuseumId(museumId));
    }
}