package com.museflow.backend.exhibition.service;

import com.museflow.backend.exhibition.model.Exhibition;
import com.museflow.backend.exhibition.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;
import com.museflow.backend.museum.repository.MuseumRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final MuseumRepository museumRepository;
    public ExhibitionService(
        ExhibitionRepository exhibitionRepository,
        MuseumRepository museumRepository) {
        this.exhibitionRepository = exhibitionRepository;  
        this.museumRepository = museumRepository;
    }

    public Exhibition createExhibition(Exhibition exhibition) {

        if (!museumRepository.existsById(exhibition.getMuseumId())) {
            throw new IllegalArgumentException("Museum does not exist");
        }
    
        LocalDate startDate = exhibition.getStartDate();
        LocalDate endDate = exhibition.getEndDate();
    
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }
    
        return exhibitionRepository.save(exhibition);
    }

    public List<Exhibition> getAllExhibitions() {
        return exhibitionRepository.findAll();
    }

    public Optional<Exhibition> getExhibitionById(String id) {
        return exhibitionRepository.findById(id);
    }

    public List<Exhibition> getExhibitionsByMuseumId(String museumId) {
        return exhibitionRepository.findByMuseumId(museumId);
    }
}