package com.museflow.backend.museum.service;

import com.museflow.backend.museum.model.Museum;
import com.museflow.backend.museum.repository.MuseumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuseumService {

    private final MuseumRepository museumRepository;

    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    public Museum createMuseum(Museum museum) {
        return museumRepository.save(museum);
    }

    public List<Museum> getAllMuseums() {
        return museumRepository.findAll();
    }

    public Optional<Museum> getMuseumById(String id) {
        return museumRepository.findById(id);
    }
}