package com.museflow.backend.artwork.service;

import com.museflow.backend.artwork.model.Artwork;
import com.museflow.backend.artwork.repository.ArtworkRepository;
import com.museflow.backend.exhibition.repository.ExhibitionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ExhibitionRepository exhibitionRepository;

    public ArtworkService(
            ArtworkRepository artworkRepository,
            ExhibitionRepository exhibitionRepository
    ) {
        this.artworkRepository = artworkRepository;
        this.exhibitionRepository = exhibitionRepository;
    }

    public Artwork createArtwork(Artwork artwork) {

        if (!exhibitionRepository.existsById(
                artwork.getExhibitionId()
        )) {
            throw new IllegalArgumentException(
                    "Exhibition does not exist"
            );
        }

        return artworkRepository.save(artwork);
    }

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public Optional<Artwork> getArtworkById(String id) {
        return artworkRepository.findById(id);
    }

    public List<Artwork> getArtworksByExhibitionId(
            String exhibitionId
    ) {
        return artworkRepository.findByExhibitionId(
                exhibitionId
        );
    }
}