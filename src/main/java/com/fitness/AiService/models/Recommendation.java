package com.fitness.AiService.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "recommendations")
@Data
@Builder
public class Recommendation {
    @Id
    private String Id;

    private UUID uuid ;
    private UUID userUuid;
    private UUID activityUuid;
    private String recommendationText;
    private List<String> tips;
    private List<String> improvements;
    private List<String> resources;
    private List<String> safetyPrecautions;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
