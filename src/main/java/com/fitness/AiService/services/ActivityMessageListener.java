package com.fitness.AiService.services;

import com.fitness.AiService.dtos.AiGeneratedRecommendationDTO;
import com.fitness.AiService.models.Activity;
import com.fitness.AiService.models.Recommendation;
import com.fitness.AiService.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final AiGenerationService aiGenerationService;
    private final RecommendationRepository recommendationRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void processActivity(Activity activity){

        // For DEBUGGING :
        log.info("Received activity with activity-uuid: {}", activity.getActivityUuid());
        System.out.println();
        System.out.println();
        System.out.println("Processing activity for AI recommendations...");
        System.out.println("Received activity: " + activity.getActivityUuid() + "name of activity: " + activity.getName() + " type of activity: " + activity.getName());
        System.out.println();

        // PROCESSING :
        try {
            log.info("Generating AI recommendations for activity: {}", activity.getActivityUuid());

            // 1. Call the AI service
            AiGeneratedRecommendationDTO aiData = aiGenerationService.generateRecommendation(activity);

            //Debug :
            System.out.println();
            System.out.println();
            log.debug("AI generated recommendations for activity: {}" , aiData);
            System.out.println();
            System.out.println();
            System.out.println();


            // 2. Map the AI response to the database model
            Recommendation recommendation =
                    Recommendation.builder()
                    .uuid(UUID.randomUUID())
                    .userUuid(activity.getUserUuid())
                    .activityUuid(activity.getActivityUuid())
                    .recommendationText(aiData.getRecommendationText())
                    .tips(aiData.getTips())
                    .improvements(aiData.getImprovements())
                    .resources(aiData.getResources())
                    .safetyPrecautions(aiData.getSafetyPrecautions())
                    .build();

            // 3. Save the new recommendation to MongoDB
            recommendationRepository.save(recommendation);

            log.info("Successfully generated and saved recommendation for activity: {}", activity.getActivityUuid());

        } catch (Exception e) {
            log.error("Failed to process AI recommendation for activity: {}", activity.getActivityUuid(), e);
            throw e;
        }


    }
}
