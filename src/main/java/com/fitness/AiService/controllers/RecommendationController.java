package com.fitness.AiService.controllers;

import com.fitness.AiService.dtos.RecommendationResponseDTO;
import com.fitness.AiService.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<RecommendationResponseDTO>> findByUserUuid(@PathVariable UUID userUuid) {
        List<RecommendationResponseDTO> recommendations = recommendationService.getAllRecommendations(userUuid);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/activity/{activityUuid}")
    public ResponseEntity<RecommendationResponseDTO> findByActivityUuid(@PathVariable UUID activityUuid) {
        return recommendationService.findByActivityUuid(activityUuid)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new RuntimeException("Recommendation not found for activity UUID: " + activityUuid));
    }
}
