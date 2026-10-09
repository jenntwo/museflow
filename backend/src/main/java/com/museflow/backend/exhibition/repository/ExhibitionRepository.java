package com.museflow.backend.exhibition.repository;

import com.museflow.backend.exhibition.model.Exhibition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExhibitionRepository
        extends MongoRepository<Exhibition, String> {

    List<Exhibition> findByMuseumId(String museumId);
}