package com.fitness.AiService.repository;

import com.fitness.AiService.models.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRepository extends MongoRepository<Recommendation,String> {

    List<Recommendation> findByUserUuid(UUID userUuid);
    Optional<Recommendation> findByActivityUuid(UUID activityUuid);
}
