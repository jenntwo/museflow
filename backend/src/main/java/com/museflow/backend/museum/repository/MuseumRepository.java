package com.museflow.backend.museum.repository;

import com.museflow.backend.museum.model.Museum;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MuseumRepository
        extends MongoRepository<Museum, String> {
}