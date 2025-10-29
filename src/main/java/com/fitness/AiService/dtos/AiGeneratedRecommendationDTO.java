package com.fitness.AiService.dtos;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AiGeneratedRecommendationDTO {
    private String recommendationText;
    private List<String> tips;
    private List<String> improvements;
    private List<String> resources;
    private List<String> safetyPrecautions;
}
