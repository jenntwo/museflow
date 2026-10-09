package com.museflow.backend.artwork.repository;

import com.museflow.backend.artwork.model.Artwork;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArtworkRepository
        extends MongoRepository<Artwork, String> {

    List<Artwork> findByExhibitionId(String exhibitionId);
}