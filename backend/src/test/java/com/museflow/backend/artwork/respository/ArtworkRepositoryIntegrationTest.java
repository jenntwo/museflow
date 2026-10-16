package com.museflow.backend.artwork.respository;

import com.museflow.backend.artwork.model.Artwork;
import com.museflow.backend.artwork.repository.ArtworkRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ArtworkRepositoryIntegrationTest {

    @Container
    static MongoDBContainer mongoDB =
            new MongoDBContainer(
                    DockerImageName.parse("mongo:7.0")
            );

    @DynamicPropertySource
    static void mongoProperties(
            DynamicPropertyRegistry registry
    ) {
        registry.add(
                "spring.mongodb.uri",
                mongoDB::getReplicaSetUrl
        );
    }

    @Autowired
    private ArtworkRepository artworkRepository;

    @AfterEach
    void cleanUp() {
        artworkRepository.deleteAllById(
                artworkRepository
                        .findByExhibitionId("test-exhibition-001")
                        .stream()
                        .map(Artwork::getId)
                        .toList());

        artworkRepository.deleteAllById(
                artworkRepository
                        .findByExhibitionId("test-exhibition-002")
                        .stream()
                        .map(Artwork::getId)
                        .toList());
    }

    private Artwork createArtwork(
            String exhibitionId,
            String title) {
        return new Artwork(
                exhibitionId,
                title,
                "Vincent van Gogh",
                1889,
                "Oil on canvas",
                "Test artwork description",
                null);
    }

    @Test
    void shouldSaveArtwork() {

        Artwork artwork = createArtwork(
                "test-exhibition-001",
                "The Starry Night");

        Artwork saved = artworkRepository.save(artwork);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFindArtworkById() {

        Artwork saved = artworkRepository.save(
                createArtwork(
                        "test-exhibition-001",
                        "The Starry Night"));

        var found = artworkRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(
                "The Starry Night",
                found.get().getTitle());
    }

    @Test
    void shouldFindArtworksByExhibitionId() {

        artworkRepository.save(
                createArtwork(
                        "test-exhibition-001",
                        "Artwork A"));

        artworkRepository.save(
                createArtwork(
                        "test-exhibition-002",
                        "Artwork B"));

        List<Artwork> results = artworkRepository.findByExhibitionId(
                "test-exhibition-001");

        assertEquals(1, results.size());
        assertEquals(
                "Artwork A",
                results.get(0).getTitle());
    }
}