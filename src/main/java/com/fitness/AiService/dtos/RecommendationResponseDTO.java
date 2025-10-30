package com.fitness.AiService.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class RecommendationResponseDTO {
    private UUID uuid ;
    private UUID userUuid;
    private UUID activityUuid;
    private String recommendationText;
    private List<String> tips;
    private List<String> improvements;
    private List<String> resources;
    private List<String> safetyPrecautions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
