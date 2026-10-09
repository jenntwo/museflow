package com.museflow.backend.artwork.controller;

import com.museflow.backend.artwork.dto.CreateArtworkRequest;
import com.museflow.backend.artwork.model.Artwork;
import com.museflow.backend.artwork.service.ArtworkService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    // Create Artwork
    @PostMapping("/artworks")
    public ResponseEntity<Artwork> createArtwork(
            @Valid @RequestBody CreateArtworkRequest request
    ) {

        Artwork artwork = new Artwork(
                request.getExhibitionId(),
                request.getTitle(),
                request.getArtist(),
                request.getYearCreated(),
                request.getMedium(),
                request.getDescription(),
                request.getImageUrl()
        );

        Artwork createdArtwork =
                artworkService.createArtwork(artwork);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdArtwork);
    }

    // Get All Artworks
    @GetMapping("/artworks")
    public ResponseEntity<List<Artwork>> getAllArtworks() {

        return ResponseEntity.ok(
                artworkService.getAllArtworks()
        );
    }

    // Get Artwork By ID
    @GetMapping("/artworks/{id}")
    public ResponseEntity<Artwork> getArtworkById(
            @PathVariable String id
    ) {

        return artworkService
                .getArtworkById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    // Get Artworks By Exhibition ID
    @GetMapping("/exhibitions/{exhibitionId}/artworks")
    public ResponseEntity<List<Artwork>> getArtworksByExhibitionId(
            @PathVariable String exhibitionId
    ) {

        return ResponseEntity.ok(
                artworkService.getArtworksByExhibitionId(exhibitionId)
        );
    }
}