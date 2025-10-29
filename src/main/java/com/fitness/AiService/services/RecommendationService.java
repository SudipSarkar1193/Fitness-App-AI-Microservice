package com.fitness.AiService.services;

import com.fitness.AiService.dtos.RecommendationResponseDTO;
import com.fitness.AiService.models.Recommendation;
import com.fitness.AiService.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<RecommendationResponseDTO> getAllRecommendations(UUID userUuid) {
        List<Recommendation> recommendations = recommendationRepository.findByUserUuid(userUuid);
        return mapToDTO(recommendations);

    }

    public Optional<RecommendationResponseDTO> findRecommendationByActivityUuid(UUID activityUuid) {
        Optional<Recommendation> recommendation = recommendationRepository.findByActivityUuid(activityUuid);
        return recommendation.map(this::mapToDTO);
    }

    private RecommendationResponseDTO mapToDTO(Recommendation recommendation) {
        return RecommendationResponseDTO.builder()
                .uuid(recommendation.getUuid())
                .userUuid(recommendation.getUserUuid())
                .activityUuid(recommendation.getActivityUuid())
                .recommendationText(recommendation.getRecommendationText())
                .tips(recommendation.getTips())
                .improvements(recommendation.getImprovements())
                .resources(recommendation.getResources())
                .safetyPrecautions(recommendation.getSafetyPrecautions())
                .createdAt(recommendation.getCreatedAt())
                .updatedAt(recommendation.getUpdatedAt())
                .build();
    }

    private List<RecommendationResponseDTO> mapToDTO(List<Recommendation> recommendations) {
        List<RecommendationResponseDTO> recommendationResponseDTOList = new ArrayList<>();
        for(Recommendation recommendation : recommendations) {
            recommendationResponseDTOList.add(mapToDTO(recommendation));
        }
        return recommendationResponseDTOList;
    }


}
