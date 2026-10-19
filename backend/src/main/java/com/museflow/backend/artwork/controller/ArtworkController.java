package com.museflow.backend.artwork.controller;

import com.museflow.backend.artwork.dto.CreateArtworkRequest;
import com.museflow.backend.artwork.model.Artwork;
import com.museflow.backend.artwork.service.ArtworkService;
import com.museflow.backend.exception.ResourceNotFoundException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Artworks", description = "APIs for managing museum artworks")
public class ArtworkController {

        private final ArtworkService artworkService;

        public ArtworkController(ArtworkService artworkService) {
                this.artworkService = artworkService;
        }

        @Operation(summary = "Create a new artwork", description = "Create an artwork and associate it with an exhibition.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Artwork created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid artwork data")
        }) // Create Artwork
        @PostMapping("/artworks")
        public ResponseEntity<Artwork> createArtwork(
                        @Valid @RequestBody CreateArtworkRequest request) {

                Artwork artwork = new Artwork(
                                request.getExhibitionId(),
                                request.getTitle(),
                                request.getArtist(),
                                request.getYearCreated(),
                                request.getMedium(),
                                request.getDescription(),
                                request.getImageUrl());

                Artwork createdArtwork = artworkService.createArtwork(artwork);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(createdArtwork);
        }

        // Get All Artworks
        @GetMapping("/artworks")
        public ResponseEntity<List<Artwork>> getAllArtworks() {

                return ResponseEntity.ok(
                                artworkService.getAllArtworks());
        }

        @Operation(summary = "Get artwork by ID", description = "Retrieve detailed information about an artwork using its unique ID.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Artwork retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "Artwork not found")
        })
        // Get Artwork By ID
        @GetMapping("/artworks/{id}")
        public ResponseEntity<Artwork> getArtworkById(
                        @PathVariable String id) {

                Artwork artwork = artworkService
                                .getArtworkById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Artwork not found: " + id));

                return ResponseEntity.ok(artwork);
        }

        

        @Operation(summary = "Get artworks by exhibition", description = "Retrieve all artworks belonging to a specific exhibition.")
        @ApiResponse(responseCode = "200", description = "Artworks retrieved successfully") // Get Artworks By
                                                                                            // Exhibition ID
        @GetMapping("/exhibitions/{exhibitionId}/artworks")
        public ResponseEntity<List<Artwork>> getArtworksByExhibitionId(
                        @PathVariable String exhibitionId) {

                return ResponseEntity.ok(
                                artworkService.getArtworksByExhibitionId(exhibitionId));
        }
}